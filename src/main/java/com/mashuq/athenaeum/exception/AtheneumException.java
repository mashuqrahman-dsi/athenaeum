package com.mashuq.athenaeum.exception;

import com.mashuq.athenaeum.constant.ExceptionReasons;

public class AtheneumException extends RuntimeException {

	private ExceptionReasons exceptionReason;

	public AtheneumException(ExceptionReasons exceptionReason) {
		this.exceptionReason = exceptionReason;
	}

	public AtheneumException(ExceptionReasons exceptionReason, String message) {
		super(message);
		this.exceptionReason = exceptionReason;
	}

	public AtheneumException(ExceptionReasons exceptionReason, Exception ex,
			String message) {
		super(message, ex);
		this.exceptionReason = exceptionReason;
	}

	public AtheneumException(ExceptionReasons exceptionReason, Exception ex) {
		super(ex);
		this.exceptionReason = exceptionReason;
	}
}
