package com.mashuq.athenaeum.reference;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.mashuq.athenaeum.constant.ExceptionReasons;
import com.mashuq.athenaeum.exception.AtheneumException;

public abstract class AbstractRESTReference implements Reference {

	@Override
	public String retrieveData(String isbn13, String isbn10) {
		if (null == isbn13 && null == isbn10)
			throw new AtheneumException(ExceptionReasons.INVALID_ARGUMENT, "ISBN 10 or ISBN 13 is required");
		try {
			ResponseEntity<String> response = null;
			if (null != isbn13) {
				response = restCall(isbn13);
				if (!response.getStatusCode().equals(HttpStatus.OK)) {
					response = restCall(isbn10);
					if (!response.getStatusCode().equals(HttpStatus.OK))
						throw new AtheneumException(ExceptionReasons.SERVICE_CALL_FAILED);
				}
			} else {
				response = restCall(isbn10);
				if (!response.getStatusCode().equals(HttpStatus.OK))
					throw new AtheneumException(ExceptionReasons.SERVICE_CALL_FAILED);
			}
			return response.getBody();

		} catch (Exception e) {
			e.printStackTrace();
			throw new AtheneumException(ExceptionReasons.SERVICE_CALL_FAILED, e);
		}
	}

	private ResponseEntity<String> restCall(String isbn) {
		String URL = String.format(getRestURL(), isbn);
		System.out.println("URL "+URL);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.getForEntity(URL, String.class);
		return response;
	}

	public abstract String getRestURL();

}
