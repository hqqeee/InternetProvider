package com.epam.dataaccess.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Transaction {
	private int id;
	private int userId;
	Timestamp timestamp;
	private BigDecimal amount;
	private String description;
	public Transaction() {}
	
	public Transaction(int id, int userId, Timestamp timestamp, BigDecimal amount, String description) {
		super();
		this.id = id;
		this.userId = userId;
		this.timestamp = timestamp;
		this.amount = amount;
		this.description = description;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
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
	
	
}
