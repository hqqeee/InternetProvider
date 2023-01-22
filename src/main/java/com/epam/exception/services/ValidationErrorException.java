package com.epam.exception.services;

import java.util.List;

public class ValidationErrorException extends Exception {
	private static final long serialVersionUID = 1L;
	private List<String> errors;
	
	public ValidationErrorException(List<String> errors) {
		super("Incorrect fields");
		this.errors = errors;
	}
	
	public List<String> getErrors(){
		return errors;
	}
	
}
