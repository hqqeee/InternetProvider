package com.epam.exception.services;

public class UserAlreadyHasTariffException extends Exception{

	private static final long serialVersionUID = 1L;

	public UserAlreadyHasTariffException(String errMessage) {
		super(errMessage);
	}
	public UserAlreadyHasTariffException(String errMessage, Throwable e) {
		super(errMessage,e);
	}
}
