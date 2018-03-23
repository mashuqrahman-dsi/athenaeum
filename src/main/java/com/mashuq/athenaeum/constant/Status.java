package com.mashuq.athenaeum.constant;

public enum Status {
	DUMMY;
	public enum Information {
		PROCESSING, SUCCESS, FAILURE, ERROR
	};
	public enum Request {
		REQUESTED, PROCESSING, COMPLETED
	}
}
