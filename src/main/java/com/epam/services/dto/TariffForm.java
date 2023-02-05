package com.epam.services.dto;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * TariffForm class. Contains fields required to add new tariff.
 * Should be validate.
 * 
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */
public class TariffForm {
	private String name;
	private int paymentPeriod;
	private BigDecimal rate;
	private Service service;
	private String description;

	public TariffForm(String name, int paymentPeriod, BigDecimal rate, Service service, String description) {
		super();
		this.name = name;
		this.paymentPeriod = paymentPeriod;
		this.rate = rate;
		this.service = service;
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

	public Service getService() {
		return service;
	}

	public void setServiceId(Service service) {
		this.service = service;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "TariffForm [name=" + name + ", paymentPeriod=" + paymentPeriod + ", rate=" + rate + ", service="
				+ service + ", description=" + description + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, name, paymentPeriod, rate, service);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TariffForm other = (TariffForm) obj;
		return Objects.equals(description, other.description) && Objects.equals(name, other.name)
				&& paymentPeriod == other.paymentPeriod && Objects.equals(rate, other.rate) && service == other.service;
	}

}
