package com.unosquare.demo.accountmanagement.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity
@Table(name="accounts")
public class AccountDO {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="number", length=12)
	private Long number; // account number
	
	@NotBlank
	@Column(name="accountPin", length=4)
	@Pattern(regexp="[0-9]{4}")
	private String accountPin; // 4 digit code; non zero; required
	
	private Double balance;
	
	// TODO: Ver como limitarlo a 5 registros 
	//@OneToMany
	//@JoinTable(name="SELECT * FROM transactions WHERE ??? ORDER BY date desc LIMIT 5")
	@OneToMany(cascade=CascadeType.ALL, mappedBy="accountDO")
	private List<TransactionDO> transactionDOList; // Latest 5 transactions
	
	@OneToOne(optional=false)
	@JoinColumn(name="id")
	private HolderDO holderDO;
	
	
	public Long getNumber() {
		return number;
	}
	public void setNumber(Long number) {
		this.number = number;
	}
	public String getAccountPin() {
		return accountPin;
	}
	public void setAccountPin(String accountPin) {
		this.accountPin = accountPin;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public List<TransactionDO> getTransactionDOList() {
		return transactionDOList;
	}
	public void setTransactionDOList(List<TransactionDO> transactionDOList) {
		this.transactionDOList = transactionDOList;
	}
	
	
}
