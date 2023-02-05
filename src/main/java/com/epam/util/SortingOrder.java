package com.epam.util;

/**
 * 
 * Enum that represents sorting order of elements.
 * 
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */
public enum SortingOrder {
	DESC("DESC"), ASC("ASC"), DEFAULT("");

	private String order;

	private SortingOrder(String order) {
		this.order = order;
	}

	public String getOrder() {
		return order;
	}

}
