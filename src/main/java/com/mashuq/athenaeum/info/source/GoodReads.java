package com.mashuq.athenaeum.info.source;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mashuq.athenaeum.info.AbstractRESTInformationSource;

@Component
public class GoodReads extends AbstractRESTInformationSource {
	
	@Value("${goodreads.resturl}")
	String restURL;

	@Override
	public String getUniqueSourceName() {
		return "Goodreads";
	}

	@Override
	public String getRestURL() {
		return restURL;
	}
}
