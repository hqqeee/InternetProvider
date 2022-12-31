package com.epam.exception.dao;

public class DAOReadException extends DAOException {
	private static final long serialVersionUID = 1L;

	public DAOReadException() {

	}

	public DAOReadException(String errMessage, Throwable err) {
		super(errMessage, err);
	}

	public DAOReadException(Throwable err) {
		super(err);
	}
}
