package com.mashuq.athenaeum.test.jooq;

import java.io.IOException;

import org.apache.lucene.queryparser.classic.ParseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mashuq.athenaeum.service.BookService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookServiceTest {
	
	@Autowired
	private BookService bookService;

	@Test
	public void searchBooksByTitleTest() {
		try {
			System.out.println(bookService.searchBooksByTitle("Robin hood"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
