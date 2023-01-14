package com.epam.exception.services;

public class UserAlreadyExistException extends Exception{

	private static final long serialVersionUID = 1L;

	private String field;
	
	public UserAlreadyExistException(String field, String value) {
		super("User with "+ field + ": " + value + " is already registrated.");
	}
	
	public String getField() {
		return field;
	}
}
