package com.epam.exception.controller;
/**
 * A controller exception that should be thrown if the required command is not found.
 * 
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */
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
