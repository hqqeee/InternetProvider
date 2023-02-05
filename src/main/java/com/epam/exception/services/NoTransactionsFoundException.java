package com.epam.exception.services;

/**
 * A service exception that will be thrown if an attempt to retrieve a user
 * transaction results in an empty collection.
 * 
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */
public class NoTransactionsFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public NoTransactionsFoundException(String errMessage) {
		super(errMessage);
	}
}
