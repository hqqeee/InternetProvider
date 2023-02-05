package com.epam.exception.services;

import java.math.BigDecimal;

/**
 * A Service exception that will be thrown if an attempt to change a user's
 * balance results in a negative user balance.
 * 
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */
public class NegativeUserBalanceException extends Exception {

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
