package com.epam.exception.dao;

/**
 * The DAO exception to be thrown if an attempt to insert a record to the
 * persistence layer fails.
 * 
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */
public class DAOInsertException extends DAOException {
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
