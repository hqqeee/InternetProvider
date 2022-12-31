package com.epam.exception.services;

public class ServiceServiceException  extends Exception {

	private static final long serialVersionUID = 1L;

	public ServiceServiceException() {

	}
	
	public ServiceServiceException(String errMessage, Throwable err) {
		super(errMessage, err);
	}
	
	
	public ServiceServiceException(String errMessage) {
		super(errMessage);
	}

	public ServiceServiceException(Throwable err) {
		super(err);
	}
}
