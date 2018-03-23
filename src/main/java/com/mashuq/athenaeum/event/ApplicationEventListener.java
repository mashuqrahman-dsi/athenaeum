package com.mashuq.athenaeum.event;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.mashuq.athenaeum.domain.athenaeum.tables.Book;
import com.mashuq.athenaeum.domain.athenaeum.tables.records.BookRecord;

@Component
public class ApplicationEventListener {

	private static final String ISBN_13 = "isbn_13";
	private static final String ISBN_10 = "isbn_10";
	private static final String TITLE = "title";

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ApplicationEventListener.class);

	@Autowired
	private DSLContext dsl;

	@Value("${openlibrary.file}")
	private String filePath;

	@EventListener
	public void onApplicationEvent(ContextRefreshedEvent event) {
		initializeAllBooks();
	}

	private void initializeAllBooks() {
		int totalBooks = dsl.selectCount().from(Book.BOOK).fetchOne(0,
				int.class);

		if (totalBooks > 0)
			return; // already initialized.

		LineIterator it = null;
		int count = 1;

		List<BookRecord> bookRecords = new ArrayList<>();
		try {

			it = FileUtils.lineIterator(new File(filePath), "UTF-8");
			while (it.hasNext()) {
				String line = it.nextLine();
				if (null == line)
					continue;

				String[] split = line.split("\t");
				if (null == split || split.length != 5)
					continue;

				BookRecord bookRecord = createBookRecord(split[4]);
				if (null == bookRecord)
					continue;

				bookRecords.add(bookRecord);
				count++;

				if (count % 10000 == 0) {
					dsl.batchStore(bookRecords).execute();
					bookRecords.clear();
					LOGGER.info("Records inserted " + count);
				}
			}
			if (!bookRecords.isEmpty())
				dsl.batchStore(bookRecords).execute();

			LOGGER.info("Total Records inserted " + count);
			it.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private BookRecord createBookRecord(String json) {
		Gson gson = new Gson();
		Map<String, Object> map = new HashMap<String, Object>();
		map = gson.fromJson(json, map.getClass());

		if (null == map.get(TITLE)
				|| (null == map.get(ISBN_10) && null == map.get(ISBN_13)))
			return null;

		BookRecord bookRecord = dsl.newRecord(Book.BOOK);
		bookRecord.setTitle((String) map.get(TITLE));
		bookRecord.setSubtitle((String) map.get("subtitle"));
		bookRecord.setIsbn10(null != map.get(ISBN_10)
				&& !((List) map.get(ISBN_10)).isEmpty()
						? (String) ((List) map.get(ISBN_10)).get(0)
						: null);
		bookRecord.setIsbn13(null != map.get(ISBN_13)
				&& !((List) map.get(ISBN_13)).isEmpty()
						? (String) ((List) map.get(ISBN_13)).get(0)
						: null);
		bookRecord.setIndexed((byte) 0);
		return bookRecord;
	}

}
