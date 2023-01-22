package com.epam.exception.services;

public class TransactionServiceException extends Exception {

	private static final long serialVersionUID = 1L;

	public TransactionServiceException(String errMessage, Throwable err) {
		super(errMessage, err);
	}
	

}
