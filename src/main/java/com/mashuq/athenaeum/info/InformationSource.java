package com.mashuq.athenaeum.info;

public interface InformationSource {
	String getUniqueSourceName();

	String retrieveData(String isbn13, String isbn10);

}
