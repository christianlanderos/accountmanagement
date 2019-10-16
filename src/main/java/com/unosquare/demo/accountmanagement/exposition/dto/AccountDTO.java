package com.unosquare.demo.accountmanagement.exposition.dto;

import java.io.Serializable;
import java.util.List;

public class AccountDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1417574216400486521L;
	private Long number; // account number
	private Integer pin; // 4 digit code; non zero; required
	private Double balance;
	private List<TransactionDTO> transactionDTOList; // Latest 5 transactions
	private HolderDTO holderDTO;
	
	
	public Long getNumber() {
		return number;
	}
	public void setNumber(Long number) {
		this.number = number;
	}
	public Integer getPin() {
		return pin;
	}
	public void setPin(Integer pin) {
		this.pin = pin;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public List<TransactionDTO> getTransactionDTOList() {
		return transactionDTOList;
	}
	public void setTransactionDTOList(List<TransactionDTO> transactionDTOList) {
		this.transactionDTOList = transactionDTOList;
	}
	public HolderDTO getHolderDTO() {
		return holderDTO;
	}
	public void setHolderDTO(HolderDTO holderDTO) {
		this.holderDTO = holderDTO;
	}
	
	
	
}
