package com.mashuq.athenaeum.test.jooq;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.mashuq.athenaeum.service.BookService;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(properties = "scheduling.enabled=false")
@ContextConfiguration
public class BookAPITest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private BookService bookService;

	@Test
	public void searchBooksByTitleFoundTest() throws Exception {
		mvc.perform(get("/api/booksearch/title/tintin")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is3xxRedirection()).andExpect(content()
						.contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}

	@Test
	public void searchBooksByTitleNotFoundTest() throws Exception {
		mvc.perform(get("/api/booksearch/title/hattimatimtim")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());

	}

	@Test
	public void searchBooksByISBNFoundTest() throws Exception {
		mvc.perform(get("/api/booksearch/isbn/9780762778300")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is3xxRedirection()).andExpect(content()
						.contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}

	@Test
	public void searchBooksByISBNNotFoundTest() throws Exception {
		mvc.perform(get("/api/booksearch/isbn/xxxxxxxxxxxxxx")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());

	}

}
