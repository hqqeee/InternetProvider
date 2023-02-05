package com.epam.exception.dao;

/**
 * The DAO exception to be thrown if record to be inserted already exists.
 * 
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */
public class DAORecordAlreadyExistsException extends DAOException {

	private static final long serialVersionUID = 1L;

	public DAORecordAlreadyExistsException() {

	}

	public DAORecordAlreadyExistsException(String errMessage) {
		super(errMessage);
	}

	public DAORecordAlreadyExistsException(String errMessage, Throwable err) {
		super(errMessage, err);
	}

	public DAORecordAlreadyExistsException(Throwable err) {
		super(err);
	}
}
