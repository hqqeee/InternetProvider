package com.epam.exception.services;

/**
 * A service exception that will be thrown when trying to get not existing user.
 * 
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */
public class UserNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;

	public UserNotFoundException(String login) {
		super("User with login " + login + " not found.");
	}

	public UserNotFoundException(int id) {
		super("User with id " + id + " not found.");
	}

}
