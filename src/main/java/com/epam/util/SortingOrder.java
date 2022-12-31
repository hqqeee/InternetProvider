package com.epam.util;

public enum SortingOrder {
	DESC("DESC"),
	ASC("ASC"),
	DEFAULT("");
	
	private String order;
	private SortingOrder(String order) {
		this.order=order;
	}
	public String getOrder() {
		return order;
	}
	
}
