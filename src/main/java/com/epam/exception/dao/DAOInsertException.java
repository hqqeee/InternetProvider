package com.epam.exception.dao;

public class DAOInsertException extends DAOException{
	private static final long serialVersionUID = 1L;

	public DAOInsertException() {

	}
	
	public DAOInsertException(String errMessage, Throwable err) {
		super(errMessage, err);
	}

	public DAOInsertException(Throwable err) {
		super(err);
	}
}
