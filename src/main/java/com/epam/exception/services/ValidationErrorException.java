package com.epam.exception.services;

import java.util.List;

/**
 * A service exception that will be thrown when validation failed.
 * 
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */
public class ValidationErrorException extends Exception {
	private static final long serialVersionUID = 1L;
	/**
	 * List of validations errors.
	 */
	private List<String> errors;

	public ValidationErrorException(List<String> errors) {
		super("Incorrect fields");
		this.errors = errors;
	}

	/**
	 * Returns validation errors.
	 * @return validation errors.
	 */
	public List<String> getErrors() {
		return errors;
	}

}
