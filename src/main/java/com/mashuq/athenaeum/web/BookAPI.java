package com.mashuq.athenaeum.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mashuq.athenaeum.exception.AtheneumException;
import com.mashuq.athenaeum.service.BookService;

@RestController
public class BookAPI {

	@Autowired
	private BookService bookService;

	@RequestMapping(value = "/search/title/{searchTitle}", method = RequestMethod.GET)
	ResponseEntity<List<Map>> searchByTitle(
			@PathVariable("searchTitle") String searchTitle) {
		try {
			List<Map> result = bookService.searchBooksByTitle(searchTitle);
			return new ResponseEntity(result, HttpStatus.FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}

	@RequestMapping(value = "/search/isbn/{isbn}", method = RequestMethod.GET)
	ResponseEntity<List<Map>> searchByISBN(@PathVariable("isbn") String isbn) {
		try {
			List<Map> result = bookService.searchBooksByISBN(isbn);
			return new ResponseEntity(result, HttpStatus.FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}

	@RequestMapping(value = "/bookinformation/{bookID}", method = RequestMethod.GET)
	ResponseEntity<Map> getBookInformation(
			@PathVariable("bookID") Integer bookID) {
		try {
			Map result = bookService.getBookInformation(bookID);
			if (null != result)
				return new ResponseEntity(result, HttpStatus.FOUND);

			return new ResponseEntity(
					"Information about this book is not yet in our system, We have created a request and will collect the information very soon. Please call this URL again soon.",
					HttpStatus.NOT_FOUND);
		} catch (AtheneumException e) {
			e.printStackTrace();
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}