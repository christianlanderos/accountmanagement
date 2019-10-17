package com.unosquare.demo.accountmanagement.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="transactions")
public class TransactionDO {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id; // transaction ID
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	
	@ManyToOne
	@JoinColumn(name="transaction_type_id", nullable=false)
	private TransactionTypeDO transactionTypeDO; // deposit, withdrawal, debit, checks
	
	@NotNull
	private Double amount;
	
	private String description; // i.e. Movie tickets, Gasoline, deposit, etc.
	
	@ManyToOne
	@JoinColumn(name="number", nullable=false)
	private AccountDO accountDO;
	
	
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
	public TransactionTypeDO getTransactionTypeDO() {
		return transactionTypeDO;
	}
	public void setTransactionTypeDO(TransactionTypeDO transactionTypeDO) {
		this.transactionTypeDO = transactionTypeDO;
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
	public AccountDO getAccountDO() {
		return accountDO;
	}
	public void setAccountDO(AccountDO accountDO) {
		this.accountDO = accountDO;
	}
	
	
	
}
