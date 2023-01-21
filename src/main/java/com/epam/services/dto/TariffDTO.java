package com.epam.services.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class TariffDTO {
	private int id;
	private String name;
	private String description;
	private int paymentPeriod;
	private BigDecimal rate;
	private Service service;

	public TariffDTO() {
	}

	public TariffDTO(int id, String name, String description, int paymentPeriod, BigDecimal rate, Service service) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.paymentPeriod = paymentPeriod;
		this.rate = rate;
		this.service = service;
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

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, id, name, paymentPeriod, rate, service);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TariffDTO other = (TariffDTO) obj;
		return Objects.equals(description, other.description) && id == other.id && Objects.equals(name, other.name)
				&& paymentPeriod == other.paymentPeriod && Objects.equals(rate, other.rate) && service == other.service;
	}
	
	

}
