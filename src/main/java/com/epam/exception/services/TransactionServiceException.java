package com.epam.exception.services;
/**
 * The general Transaction service exception.
 * 
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */
public class TransactionServiceException extends Exception {

	private static final long serialVersionUID = 1L;

	public TransactionServiceException(String errMessage, Throwable err) {
		super(errMessage, err);
	}
	

}
