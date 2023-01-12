package com.epam.exception.controller;

public class CommandNotFoundException extends Exception{

	private static final long serialVersionUID = 1L;
	public CommandNotFoundException() {

	}
	
	public CommandNotFoundException(String errMessage, Throwable err) {
		super(errMessage, err);
	}

	public CommandNotFoundException(String err) {
		super(err);
	}
}
