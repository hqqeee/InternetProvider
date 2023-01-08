package com.epam.services.forms;

import java.math.BigDecimal;

public class TariffForm {
	private String name;
	private int paymentPeriod;
	private BigDecimal rate;
	private int serviceId;
    private String description;
	public TariffForm(String name, int paymentPeriod, BigDecimal rate, int serviceId, String description) {
		super();
		this.name = name;
		this.paymentPeriod = paymentPeriod;
		this.rate = rate;
		this.serviceId = serviceId;
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPaymentPeriod() {
		return paymentPeriod;
	}
	public void setPaymentPeriod(int paymentPeriod) {
		this.paymentPeriod = paymentPeriod;
	}
	public BigDecimal getRate() {
		return rate;
	}
	public void setRate(BigDecimal rate) {
		this.rate = rate;
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
