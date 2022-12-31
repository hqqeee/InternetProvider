package com.epam.exception.services;

public class UserAlreadyExistException extends Exception{

	private static final long serialVersionUID = 1L;

	public UserAlreadyExistException(String login) {
		super("User with login " + login + " is already registrated.");
	}
}
