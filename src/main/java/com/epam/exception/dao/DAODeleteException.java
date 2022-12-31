package com.epam.exception.dao;

public class DAODeleteException extends DAOException{
	private static final long serialVersionUID = 1L;

	public DAODeleteException() {

	}
	
	public DAODeleteException(String errMessage, Throwable err) {
		super(errMessage, err);
	}

	public DAODeleteException(Throwable err) {
		super(err);
	}
}
