package com.epam.dataaccess.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
/**
 * Tariff entity class. Matches table 'tariff' in the persistence layer.
 *
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */
public class Tariff implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private String description;
	private int paymentPeriod;
	private BigDecimal rate;
	private int serviceId;

	public Tariff() {
	}

	public Tariff(int id, String name, String description, int paymentPeriod, BigDecimal rate, int serviceId) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.paymentPeriod = paymentPeriod;
		this.rate = rate;
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

	@Override
	public String toString() {
		return "Tariff [id=" + id + ", name=" + name + ", description=" + description + ", paymentPeriod="
				+ paymentPeriod + ", rate=" + rate + ", serviceId=" + serviceId + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, id, name, paymentPeriod, rate, serviceId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tariff other = (Tariff) obj;
		return Objects.equals(description, other.description) && id == other.id && Objects.equals(name, other.name)
				&& paymentPeriod == other.paymentPeriod && Objects.equals(rate, other.rate)
				&& serviceId == other.serviceId;
	}
	
	

	
}
