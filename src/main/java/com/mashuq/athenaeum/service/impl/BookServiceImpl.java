package com.mashuq.athenaeum.service.impl;

import static com.mashuq.athenaeum.constant.BookFields.BOOKID;
import static com.mashuq.athenaeum.constant.BookFields.ISBN10;
import static com.mashuq.athenaeum.constant.BookFields.ISBN13;
import static com.mashuq.athenaeum.constant.BookFields.SUBTITLE;
import static com.mashuq.athenaeum.constant.BookFields.TITLE;
import static com.mashuq.athenaeum.domain.athenaeum.tables.Information.INFORMATION;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.bson.types.ObjectId;
import org.jooq.DSLContext;
import org.jooq.Record3;
import org.jooq.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mashuq.athenaeum.constant.ExceptionReasons;
import com.mashuq.athenaeum.constant.Status;
import com.mashuq.athenaeum.domain.athenaeum.tables.Book;
import com.mashuq.athenaeum.domain.athenaeum.tables.Request;
import com.mashuq.athenaeum.domain.athenaeum.tables.records.RequestRecord;
import com.mashuq.athenaeum.exception.AtheneumException;
import com.mashuq.athenaeum.service.BookService;
import com.mashuq.athenaeum.util.LuceneUtil;
import com.mashuq.athenaeum.util.SanitizationUtil;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

@Service
public class BookServiceImpl implements BookService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(BookServiceImpl.class);

	@Autowired
	private IndexSearcher indexSearcher;

	@Autowired
	private Analyzer analyzer;

	@Autowired
	private DSLContext dsl;

	@Autowired
	MongoDatabase mongoDatabase;

	@Override
	public List<Map> searchBooksByTitle(String title)
			throws ParseException, IOException {
		MultiFieldQueryParser queryParser = new MultiFieldQueryParser(
				new String[]{TITLE.name(), SUBTITLE.name()}, analyzer);
		Query query = queryParser.parse(SanitizationUtil.sanitize(title));

		TopDocs topDocs = indexSearcher.search(query, 100);
		List<Document> documents = LuceneUtil.createDocuments(topDocs,
				indexSearcher);
		List<Map> result = new ArrayList<>();
		for (Document document : documents) {
			result.add(createSearchResult(document));
		}
		return result;
	}

	@Override
	public List<Map> searchBooksByISBN(String isbn)
			throws ParseException, IOException {
		MultiFieldQueryParser queryParser = new MultiFieldQueryParser(
				new String[]{ISBN10.name(), ISBN13.name()}, analyzer);
		Query query = queryParser.parse(SanitizationUtil.sanitize(isbn));

		TopDocs topDocs = indexSearcher.search(query, 100);
		List<Document> documents = LuceneUtil.createDocuments(topDocs,
				indexSearcher);
		List<Map> result = new ArrayList<>();
		for (Document document : documents) {
			result.add(createSearchResult(document));
		}
		return result;
	}

	@Override
	@Transactional
	public Map getBookInformation(Integer bookID) {
		if (!dsl.fetchExists(dsl.select().from(Book.BOOK)
				.where(Book.BOOK.BOOKID.eq(bookID)))) {
			throw new AtheneumException(ExceptionReasons.RECORD_NOT_FOUND,
					"Provided BookID is not present in our records");
		}
		RequestRecord requestRecord = dsl.selectFrom(Request.REQUEST)
				.where(Request.REQUEST.BOOKID.eq(bookID)).fetchOne();
		if (null == requestRecord) {
			requestRecord = dsl.newRecord(Request.REQUEST);
			requestRecord.setBookid(bookID);
			requestRecord.setStatus(Status.Request.REQUESTED.name());
			requestRecord.insert();
			return null;
		}

		if (Status.Request.COMPLETED.name().equals(requestRecord.getStatus())) {
			Map<String, Object> result = new HashMap<>();
			org.bson.Document document = new org.bson.Document();
			Result<Record3<String, String, String>> records = dsl
					.select(INFORMATION.REFERENCE, INFORMATION.DATA,
							INFORMATION.STATUS)
					.from(INFORMATION).where(INFORMATION.REQUESTID
							.eq(requestRecord.getRequestid()))
					.fetch();
			for (Record3 record : records) {
				if (!Status.Information.SUCCESS.name()
						.equals(record.get(INFORMATION.STATUS)))
					continue;
				MongoCollection<org.bson.Document> collection = mongoDatabase
						.getCollection(record.get(INFORMATION.REFERENCE));
				org.bson.Document doc = collection
						.find(Filters.eq("_id", new ObjectId(record.get(INFORMATION.DATA))))
						.first();
				result.put(record.get(INFORMATION.REFERENCE), doc.toJson());
			}
			return result;
		} else
			return null;

	}

	private Map<String, Object> createSearchResult(Document document) {
		Map<String, Object> map = new HashMap<>();
		map.put(TITLE.name(), document.get(TITLE.name()));
		map.put(SUBTITLE.name(), document.get(SUBTITLE.name()));
		map.put(BOOKID.name(), Integer.parseInt(document.get(BOOKID.name())));
		return map;
	}

}
