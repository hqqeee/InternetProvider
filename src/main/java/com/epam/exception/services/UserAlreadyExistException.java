package com.epam.exception.services;

/**
 * A service exception that will be thrown when trying to add an existing user.
 * 
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */
public class UserAlreadyExistException extends Exception {

	private static final long serialVersionUID = 1L;

	private String field;

	public UserAlreadyExistException(String field, String value) {
		super("User with " + field + ": " + value + " is already registrated.");
		this.field = field;
	}

	public String getField() {
		return field;
	}
}
