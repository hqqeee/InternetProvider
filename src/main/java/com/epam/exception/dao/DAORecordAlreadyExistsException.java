package com.epam.exception.dao;

public class DAORecordAlreadyExistsException extends DAOException{

	private static final long serialVersionUID = 1L;

	public DAORecordAlreadyExistsException() {

	}

	public DAORecordAlreadyExistsException(String errMessage, Throwable err) {
		super(errMessage, err);
	}

	public DAORecordAlreadyExistsException(Throwable err) {
		super(err);
	}
}
