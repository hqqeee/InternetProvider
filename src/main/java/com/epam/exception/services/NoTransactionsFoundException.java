package com.epam.exception.services;

public class NoTransactionsFoundException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public NoTransactionsFoundException(String errMessage) {
		super(errMessage);
	}
}
