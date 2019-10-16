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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import com.unosquare.demo.accountmanagement.util.Constants;

@Entity
@Table(name="accounts")
public class AccountDO {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="number", length=12)
	private Long number; // account number
	
	@NotBlank
	@Max(Constants.MAX_PIN_VALUE)
	@Min(Constants.MIN_PIN_VALUE)
	@Column(name="pin", length=4)
	private Integer pin; // 4 digit code; non zero; required
	
	private Double balance;
	
	//@OneToMany
	//@JoinTable(name="SELECT * FROM transactions WHERE ??? ORDER BY date desc LIMIT 5")
	@OneToMany(cascade=CascadeType.ALL, mappedBy="accountDO")
	private List<TransactionDO> transactionDOList; // Latest 5 transactions
	
	@OneToOne(optional=false)
	@JoinColumn(name="ssn")
	private HolderDO holderDO;
	
	private Boolean enabled;
	
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
	public List<TransactionDO> getTransactionDOList() {
		return transactionDOList;
	}
	public void setTransactionDOList(List<TransactionDO> transactionDOList) {
		this.transactionDOList = transactionDOList;
	}
	public HolderDO getHolderDO() {
		return holderDO;
	}
	public void setHolderDO(HolderDO holderDO) {
		this.holderDO = holderDO;
	}
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
	
}
