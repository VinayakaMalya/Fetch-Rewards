package com.fetch.rewards.utilties;

public class InvalidTransactionError extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidTransactionError(String message, Throwable arg1) {
		super(message, arg1);

	}

	public InvalidTransactionError(String message) {
		super(message);

	}

	public InvalidTransactionError(Throwable message) {
		super(message);

	}

}