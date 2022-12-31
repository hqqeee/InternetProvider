package com.epam.exception.services;


public class UnableToRemoveUser extends Exception{

	private static final long serialVersionUID = 1L;

	public UnableToRemoveUser(String errMessage, Throwable err) {
		super(errMessage);
		err.printStackTrace();
	}
}
