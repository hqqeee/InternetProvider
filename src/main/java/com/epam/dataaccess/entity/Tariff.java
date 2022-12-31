package com.epam.dataaccess.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class Tariff implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String name;
    private String description;
    private BigDecimal price;
    private int serviceId;

    public Tariff() {
    }

    public Tariff(int id, String name, String description, BigDecimal price, int serviceId) {
	this.id = id;
	this.name = name;
	this.description = description;
	this.price = price;
	this.serviceId = serviceId;
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
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

	@Override
	public String toString() {
		return "Tariff [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price
				+ ", serviceId=" + serviceId + "]";
	}
    
    

}
