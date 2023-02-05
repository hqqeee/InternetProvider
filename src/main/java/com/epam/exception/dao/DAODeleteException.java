package com.epam.exception.dao;
/**
 * The DAO exception to be thrown if an attempt to delete a record from the persistence layer fails.
 * 
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */
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
