package com.epam.exception.services;

/**
 * The general User service exception.
 * 
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */
public class UserServiceException extends Exception {

	private static final long serialVersionUID = 1L;

	public UserServiceException() {

	}

	public UserServiceException(String errMessage, Throwable err) {
		super(errMessage, err);
	}

	public UserServiceException(String errMessage) {
		super(errMessage);
	}

}
