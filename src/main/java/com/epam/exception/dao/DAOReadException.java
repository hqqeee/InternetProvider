package com.epam.exception.dao;

/**
 * The DAO exception to be thrown if an attempt to read a record from the
 * persistence layer fails.
 * 
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */
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
