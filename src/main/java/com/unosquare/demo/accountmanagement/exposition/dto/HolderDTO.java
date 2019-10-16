package com.unosquare.demo.accountmanagement.exposition.dto;

import java.io.Serializable;


public class HolderDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2598355896838150528L;
	private Long ssn;
	private String firstName;
	private String lastName;
	
	
	public Long getSsn() {
		return ssn;
	}
	public void setSsn(Long ssn) {
		this.ssn = ssn;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	
}
