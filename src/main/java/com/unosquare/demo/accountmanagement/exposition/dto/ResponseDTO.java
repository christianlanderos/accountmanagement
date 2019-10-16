package com.unosquare.demo.accountmanagement.exposition.dto;

import java.io.Serializable;

public class ResponseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4142850298360656128L;
	private Integer responseCode;
	private String responseMessage;
	private AccountDTO accountDTO;
	
	
	public Integer getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(Integer responseCode) {
		this.responseCode = responseCode;
	}
	public String getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	public AccountDTO getAccountDTO() {
		return accountDTO;
	}
	public void setAccountDTO(AccountDTO accountDTO) {
		this.accountDTO = accountDTO;
	}
	
	
}
