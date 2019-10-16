package com.unosquare.demo.accountmanagement.exposition.dto;

import java.io.Serializable;
import java.util.Date;

public class TransactionDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1565909020512031435L;
	private Long id; // transaction ID
	private Date date;
	private String transactionType; // deposit, withdrawal, debit, checks
	private Double amount;
	private String description; // i.e. Movie tickets, Gasoline, deposit, etc.
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
