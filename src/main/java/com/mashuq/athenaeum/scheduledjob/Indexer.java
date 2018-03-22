package com.mashuq.athenaeum.scheduledjob;

import java.io.IOException;
import java.util.Iterator;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.mashuq.athenaeum.constant.BookFields;
import com.mashuq.athenaeum.domain.athenaeum.tables.Book;
import com.mashuq.athenaeum.domain.athenaeum.tables.records.BookRecord;

@Component
public class Indexer {
	private static final Logger LOGGER = LoggerFactory.getLogger(Indexer.class);

	@Autowired
	private DSLContext dsl;

	@Autowired
	private ApplicationContext ctx;

	@Scheduled(fixedDelay = 1, initialDelay = 6000)
	public void indexBookTitles() throws IOException {
		LOGGER.info("Indexing books ...");
		Result<BookRecord> unindexedBooks = dsl.selectFrom(Book.BOOK).where(Book.BOOK.INDEXED.eq((byte) 0)).limit(1000)
				.fetch();
		if (unindexedBooks.size() <= 0) {
			LOGGER.info("Nothing to index");
			return;
		}
		IndexWriter indexWriter = ctx.getBean(IndexWriter.class);

		Iterator<BookRecord> it = unindexedBooks.iterator();
		while (it.hasNext()) {
			BookRecord bookRecord = it.next();
			indexBookRecord(bookRecord, indexWriter);
			bookRecord.setIndexed((byte) 1);
			bookRecord.update();
		}
		indexWriter.flush();
		indexWriter.commit();
		indexWriter.close();
		LOGGER.info("Indexed " + unindexedBooks.size() + " books");
	}

	public void indexBookRecord(BookRecord bookRecord, IndexWriter indexWriter) throws IOException {
		if (null == bookRecord)
			return;
		Document document = new Document();
		if (null != bookRecord.getTitle())
			document.add(new TextField(BookFields.TITLE.name(), bookRecord.getTitle(), Field.Store.YES));
		if (null != bookRecord.getSubtitle())
			document.add(new TextField(BookFields.SUBTITLE.name(), bookRecord.getSubtitle(), Field.Store.YES));
		if (null != bookRecord.getIsbn10())
			document.add(new StringField(BookFields.ISBN10.name(), bookRecord.getIsbn10(), Field.Store.YES));
		if (null != bookRecord.getIsbn13())
			document.add(new StringField(BookFields.ISBN13.name(), bookRecord.getIsbn13(), Field.Store.YES));
		document.add(new StoredField(BookFields.BOOKID.name(), bookRecord.getBookid()));
		indexWriter.addDocument(document);
	}

}
