package com.epam.services.forms;

import java.math.BigDecimal;

public class TariffForm {
	private String name;
	private BigDecimal price;
	private int serviceId;
    private String description;
    
	public TariffForm(String name, BigDecimal price, int serviceId, String description) {
		this.name = name;
		this.price = price;
		this.serviceId = serviceId;
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public int getServiceId() {
		return serviceId;
	}
	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
    
    
}
