package com.mashuq.athenaeum.scheduledjob;

import static com.mashuq.athenaeum.domain.athenaeum.tables.Book.BOOK;
import static com.mashuq.athenaeum.domain.athenaeum.tables.Information.INFORMATION;
import static com.mashuq.athenaeum.domain.athenaeum.tables.Request.REQUEST;

import java.util.List;

import org.bson.Document;
import org.jooq.DSLContext;
import org.jooq.Record2;
import org.jooq.Record5;
import org.jooq.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mashuq.athenaeum.constant.Status;
import com.mashuq.athenaeum.domain.athenaeum.tables.Information;
import com.mashuq.athenaeum.domain.athenaeum.tables.records.InformationRecord;
import com.mashuq.athenaeum.reference.Reference;
import com.mashuq.athenaeum.util.MongoUtil;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@Component
public class Processor {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(Processor.class);

	@Autowired
	List<Reference> references;

	@Autowired
	MongoDatabase mongoDatabase;

	@Autowired
	private DSLContext dsl;

	@Scheduled(fixedDelay = 10000, initialDelay = 1000)
	@Transactional
	public void processInformationRequest() {
		LOGGER.info("Processing Request ...");

		Result<Record2<Integer, String>> requested = dsl
				.select(REQUEST.REQUESTID, REQUEST.STATUS).from(REQUEST)
				.where(REQUEST.STATUS.eq(Status.Request.PROCESSING.name())
						.or(REQUEST.STATUS.eq(Status.Request.REQUESTED.name())))
				.limit(100).fetch();

		if (requested.isEmpty()) {
			LOGGER.info("No request to process");
			return;
		}

		for (Record2 record : requested) {
			if (Status.Request.REQUESTED.name()
					.equals(record.get(REQUEST.STATUS))) {
				processNewRequests(record);
			} else if (Status.Request.PROCESSING.name()
					.equals(record.get(REQUEST.STATUS))) {
				processAlreadyProcessedOrProcessing(record);
			}
		}
		LOGGER.info("Processed " + requested.size() + " requests");
	}

	@Scheduled(fixedDelay = 10000, initialDelay = 1000)
	public void processInformationSources() {
		LOGGER.info("Processing information ...");

		Result<Record5<Integer, String, String, Integer, String>> unprocessed = dsl
				.select(BOOK.BOOKID, BOOK.ISBN10, BOOK.ISBN13,
						INFORMATION.INFORMATIONID, INFORMATION.REFERENCE)
				.from(INFORMATION).join(REQUEST).onKey().join(BOOK).onKey()
				.where(INFORMATION.STATUS
						.eq(Status.Information.PROCESSING.name()))
				.fetch();

		if (unprocessed.isEmpty()) {
			LOGGER.info("No information to process");
			return;
		}

		LOGGER.info("unprocessed " + unprocessed);

		for (Record5 record : unprocessed) {
			Reference reference = getReference(
					record.get(INFORMATION.REFERENCE));

			LOGGER.info("reference " + reference);
			if (null == reference) {
				updateErrorRecord(record.get(INFORMATION.INFORMATIONID));
				continue;
			}
			processInformation(record, reference);
		}

		LOGGER.info(
				"Processed information of " + unprocessed.size() + " records");
	}

	private void processInformation(Record5 record, Reference reference) {
		if (null == record || null == reference)
			return;

		try {
			String jsonData = reference.retrieveData(record.get(BOOK.ISBN13),
					record.get(BOOK.ISBN10));

			LOGGER.info("jsonData " + jsonData);

			if (!MongoUtil.collectionExists(reference.getReferenceName(),
					mongoDatabase))
				mongoDatabase.createCollection(reference.getReferenceName());

			MongoCollection<Document> collection = mongoDatabase
					.getCollection(reference.getReferenceName());
			Document document = Document.parse(jsonData);
			collection.insertOne(document);
			String informationID = document.getObjectId("_id").toString();
			dsl.update(INFORMATION)
					.set(INFORMATION.STATUS, Status.Information.SUCCESS.name())
					.set(INFORMATION.DATA, informationID)
					.where(INFORMATION.INFORMATIONID
							.eq(record.get(INFORMATION.INFORMATIONID)))
					.execute();
		} catch (Exception e) {
			e.printStackTrace();
			dsl.update(INFORMATION)
					.set(INFORMATION.STATUS, Status.Information.FAILURE.name())
					.where(INFORMATION.INFORMATIONID
							.eq(record.get(INFORMATION.INFORMATIONID)))
					.execute();
		}
	}

	private void updateErrorRecord(Integer informationID) {
		dsl.update(INFORMATION)
				.set(INFORMATION.STATUS, Status.Information.ERROR.name())
				.where(INFORMATION.INFORMATIONID.eq(informationID)).execute();

	}

	private Reference getReference(String referenceName) {
		for (Reference reference : references)
			if (reference.getReferenceName().equals(referenceName))
				return reference;

		return null;
	}

	private void processNewRequests(Record2 record) {
		InformationRecord informationRecord = null;
		for (Reference reference : references) {
			informationRecord = dsl.newRecord(Information.INFORMATION);
			informationRecord.setStatus(Status.Information.PROCESSING.name());
			informationRecord.setReference(reference.getReferenceName());
			informationRecord.setRequestid(record.get(REQUEST.REQUESTID));
			informationRecord.insert();
		}
		dsl.update(REQUEST)
				.set(REQUEST.STATUS, Status.Request.PROCESSING.name())
				.where(REQUEST.REQUESTID.eq(record.get(REQUEST.REQUESTID)))
				.execute();
	}

	private void processAlreadyProcessedOrProcessing(Record2 record) {
		boolean allProcessed = true;

		Result<Record2<Integer, String>> processingInformation = dsl
				.select(INFORMATION.INFORMATIONID, INFORMATION.STATUS)
				.from(INFORMATION)
				.where(INFORMATION.REQUESTID.eq(record.get(REQUEST.REQUESTID)))
				.fetch();

		for (Record2 informationRecord : processingInformation) {
			if (Status.Information.PROCESSING.name()
					.equals(informationRecord.get(INFORMATION.STATUS)))
				allProcessed = false;
		}

		if (allProcessed) {
			dsl.update(REQUEST)
					.set(REQUEST.STATUS, Status.Request.COMPLETED.name())
					.where(REQUEST.REQUESTID.eq(record.get(REQUEST.REQUESTID)))
					.execute();
		}
	}

}
