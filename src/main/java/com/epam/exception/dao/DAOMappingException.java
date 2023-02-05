package com.epam.exception.dao;

/**
 * The DAO exception to be thrown if an attempt to map a record with entity
 * failed.
 * 
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */
public class DAOMappingException extends DAOException {
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
