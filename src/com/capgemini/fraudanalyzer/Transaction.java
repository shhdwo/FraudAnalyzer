package com.capgemini.fraudanalyzer;

import java.math.BigDecimal;

public class Transaction {
	private int id;
	private int userId;
	private BigDecimal amount = BigDecimal.valueOf(0);
	private String from;
	private String to;
	private String date;
	
	public Transaction(int id, int userId, BigDecimal amount, String from, String to, String date) {
		this.id = id;
		this.userId = userId;
		this.amount = amount;
		this.from = from;
		this.to = to;
		this.date = date;
	}
	
	public int getId() {
		return id;
	}
	
	public int getUserId() {
		return userId;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}
	
	public String getFrom() {
		return from;
	}
	
	public String getTo() {
		return to;
	}
	
	public String getDate() {
		return date;
	}
}
