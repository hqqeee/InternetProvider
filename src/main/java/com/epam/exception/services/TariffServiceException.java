package com.epam.exception.services;

/**
 * The general Tariff service exception.
 * 
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */
public class TariffServiceException extends Exception {

	private static final long serialVersionUID = 1L;

	public TariffServiceException(String errMessage, Throwable err) {
		super(errMessage, err);
	}

	public TariffServiceException(String errMessage) {
		super(errMessage);
	}

}
