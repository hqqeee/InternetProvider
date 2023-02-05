package com.epam.exception.services;

/**
 * A service exception that will be thrown when trying to add an existing tariff
 * to user.
 * 
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */
public class UserAlreadyHasTariffException extends Exception {

	private static final long serialVersionUID = 1L;

	public UserAlreadyHasTariffException(String errMessage, Throwable e) {
		super(errMessage, e);
	}
}
