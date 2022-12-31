package com.epam.exception.dao;

public class DAOMappingException extends DAOException{
	private static final long serialVersionUID = 1L;

	public DAOMappingException() {

	}
	
	public DAOMappingException(String errMessage, Throwable err) {
		super(errMessage, err);
	}

	public DAOMappingException(Throwable err) {
		super(err);
	}
}
