package com.epam.exception.services;

public class TariffServiceException extends Exception{

	private static final long serialVersionUID = 1L;

	public TariffServiceException() {

	}
	
	public TariffServiceException(String errMessage, Throwable err) {
		super(errMessage, err);
	}
	
	
	public TariffServiceException(String errMessage) {
		super(errMessage);
	}

	public TariffServiceException(Throwable err) {
		super(err);
	}

}
