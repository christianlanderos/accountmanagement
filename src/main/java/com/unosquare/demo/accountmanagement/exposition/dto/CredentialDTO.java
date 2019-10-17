package com.unosquare.demo.accountmanagement.exposition.dto;

import java.io.Serializable;

public class CredentialDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7100729639652733141L;
	private Long ssn;
	private Integer accountPin;
	
	
	public Long getSsn() {
		return ssn;
	}
	public void setSsn(Long ssn) {
		this.ssn = ssn;
	}
	public Integer getAccountPin() {
		return accountPin;
	}
	public void setAccountPin(Integer accountPin) {
		this.accountPin = accountPin;
	}
	
	

}
