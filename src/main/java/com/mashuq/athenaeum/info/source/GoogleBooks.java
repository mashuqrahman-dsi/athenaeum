package com.mashuq.athenaeum.info.source;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mashuq.athenaeum.info.AbstractRESTInformationSource;

@Component
public class GoogleBooks extends AbstractRESTInformationSource {
	
	@Value("${google.books.resturl}")
	String restURL;

	@Override
	public String getUniqueSourceName() {
		return "Google Books";
	}

	@Override
	public String getRestURL() {
		return restURL;
	}

}
