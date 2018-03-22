package com.mashuq.athenaeum.reference.source;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mashuq.athenaeum.constant.Reference;
import com.mashuq.athenaeum.reference.AbstractRESTReference;

@Component
public class GoodReads extends AbstractRESTReference {

	@Value("${goodreads.resturl}")
	String restURL;

	@Override
	public String getReferenceName() {
		return Reference.Goodreads.name();
	}

	@Override
	public String getRestURL() {
		return restURL;
	}
}
