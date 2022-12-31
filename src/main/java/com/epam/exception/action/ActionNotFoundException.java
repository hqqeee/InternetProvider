package com.epam.exception.action;

public class ActionNotFoundException extends Exception{

	private static final long serialVersionUID = 1L;
	public ActionNotFoundException() {

	}
	
	public ActionNotFoundException(String errMessage, Throwable err) {
		super(errMessage, err);
	}

	public ActionNotFoundException(Throwable err) {
		super(err);
	}
}
