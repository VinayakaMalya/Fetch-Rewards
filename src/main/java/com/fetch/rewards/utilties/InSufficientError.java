package com.fetch.rewards.utilties;

public class InSufficientError extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InSufficientError(String message, Throwable arg1) {
		super(message, arg1);

	}

	public InSufficientError(String message) {
		super(message);

	}

	public InSufficientError(Throwable message) {
		super(message);

	}


}
