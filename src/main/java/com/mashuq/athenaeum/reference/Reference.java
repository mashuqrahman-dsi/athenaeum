package com.mashuq.athenaeum.reference;

public interface Reference {
	String getReferenceName();

	String retrieveData(String isbn13, String isbn10);

}
