package com.epam.exception.services;

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

	public UserServiceException(Throwable err) {
		super(err);
	}
}
