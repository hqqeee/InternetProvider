package com.epam.exception.dao;

/**
 * The DAO exception to be thrown if an attempt to update a record in the
 * persistence layer fails.
 * 
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */
public class DAOUpdateException extends DAOException {
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
