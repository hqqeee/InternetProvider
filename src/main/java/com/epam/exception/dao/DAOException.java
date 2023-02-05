package com.epam.exception.dao;
/**
 * Super class for all DAO exceptions.
 * 
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */
public class DAOException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public DAOException() {
		
	}
	
	public DAOException(String errMessage) {
		super(errMessage);
	}
	
	public DAOException(String errMessage, Throwable err) {
		super(errMessage, err);
	}
	
	public DAOException(Throwable err) {
		super(err);
	}
}
