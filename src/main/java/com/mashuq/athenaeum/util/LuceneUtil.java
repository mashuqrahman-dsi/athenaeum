package com.mashuq.athenaeum.util;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

public class LuceneUtil {

	public static String getCurrentTimeStamp() {
		return LocalDateTime.now()
				.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
	}

	public static List<Document> createDocuments(TopDocs topDocs, IndexSearcher searcher) throws IOException {
		List<Document> documents = new ArrayList<>();
		for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
			documents.add(searcher.doc(scoreDoc.doc));
		}

		return documents;
	}
}
