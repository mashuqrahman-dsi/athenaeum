package com.mashuq.athenaeum.reference.source;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mashuq.athenaeum.constant.Reference;
import com.mashuq.athenaeum.reference.AbstractRESTReference;

@Component
public class GoogleBooks extends AbstractRESTReference {

	@Value("${google.books.resturl}")
	String restURL;

	@Override
	public String getReferenceName() {
		return Reference.GoogleBooks.name();
	}

	@Override
	public String getRestURL() {
		return restURL;
	}

}
