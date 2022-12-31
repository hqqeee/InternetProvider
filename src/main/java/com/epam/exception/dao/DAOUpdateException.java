package com.epam.exception.dao;

public class DAOUpdateException extends DAOException{
	private static final long serialVersionUID = 1L;

	public DAOUpdateException() {

	}
	
	public DAOUpdateException(String errMessage, Throwable err) {
		super(errMessage, err);
	}

	public DAOUpdateException(Throwable err) {
		super(err);
	}
}
