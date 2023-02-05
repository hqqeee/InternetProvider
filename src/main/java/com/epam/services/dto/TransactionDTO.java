package com.epam.services.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
/**
 * TransactionDTO class. Contains fields required by view.
 * 
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */
public class TransactionDTO {
	private int id;
	private Date timestamp;
	private BigDecimal amount;
	private String description;

	
	
	public TransactionDTO() {
	}
	public TransactionDTO(int id, Date timestamp, BigDecimal amount, String description) {
		this.id = id;
		this.timestamp = timestamp;
		this.amount = amount;
		this.description = description;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public int hashCode() {
		return Objects.hash(amount, description, id, timestamp);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TransactionDTO other = (TransactionDTO) obj;
		return Objects.equals(amount, other.amount) && Objects.equals(description, other.description) && id == other.id
				&& Objects.equals(timestamp, other.timestamp);
	}
	
	
	
}
