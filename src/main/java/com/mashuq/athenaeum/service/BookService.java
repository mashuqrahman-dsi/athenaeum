package com.mashuq.athenaeum.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.lucene.queryparser.classic.ParseException;

public interface BookService {

	List<Map> searchBooksByTitle(String title) throws ParseException, IOException;
	
	List<Map> searchBooksByISBN(String isbn) throws ParseException, IOException;
	
	Map getBookInformation(Integer bookID);
}
