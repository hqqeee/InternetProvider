package com.epam.exception.services;

public class UserNotFoundException extends Exception{
	private static final long serialVersionUID = 1L;

	public UserNotFoundException(String login) {
		super("User with login " + login + " not found.");
	}
	
	public UserNotFoundException(int id){
		super("User with id " + id + " not found.");
	}

}
