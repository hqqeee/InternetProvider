package com.epam.exception.services;

import java.math.BigDecimal;

public class NegativeUserBalanceException extends Exception{

	private BigDecimal valueUnderZero;
	
	private static final long serialVersionUID = 1L;

	public NegativeUserBalanceException(BigDecimal valueUnderZero) {
		super();
		this.valueUnderZero = valueUnderZero;
	}

	public NegativeUserBalanceException() {
	}

	public BigDecimal getValueUnderZero() {
		return valueUnderZero;
	}
	
}
