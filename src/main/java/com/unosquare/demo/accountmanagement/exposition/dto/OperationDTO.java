package com.unosquare.demo.accountmanagement.exposition.dto;

import java.io.Serializable;

public class OperationDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5953345019136550277L;

	private Long accountNumber;
	private Integer pin;
	private Double amount;
	private Long transactionType;
	private String description;
	
	
	public Long getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}
	public Integer getPin() {
		return pin;
	}
	public void setPin(Integer pin) {
		this.pin = pin;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Long getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(Long transactionType) {
		this.transactionType = transactionType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
