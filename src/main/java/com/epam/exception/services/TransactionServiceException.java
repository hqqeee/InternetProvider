package com.epam.exception.services;

public class TransactionServiceException extends Exception {

	private static final long serialVersionUID = 1L;

	public TransactionServiceException() {

	}
	
	public TransactionServiceException(String errMessage, Throwable err) {
		super(errMessage, err);
	}
	
	
	public TransactionServiceException(String errMessage) {
		super(errMessage);
	}

	public TransactionServiceException(Throwable err) {
		super(err);
	}
}
