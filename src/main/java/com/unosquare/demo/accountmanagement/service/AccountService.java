package com.unosquare.demo.accountmanagement.service;

import com.unosquare.demo.accountmanagement.model.AccountDO;
import com.unosquare.demo.accountmanagement.model.HolderDO;


public interface AccountService {

	AccountDO authenticate(Long ssn, Integer accountPin) throws IllegalArgumentException;
	AccountDO openAccount(HolderDO holderDO) throws IllegalArgumentException;
	AccountDO closeAccount(Long accountNumber) throws IllegalArgumentException, UnsupportedOperationException;
	AccountDO deposit(Long accountNumber, Integer pin, Double amount) throws IllegalArgumentException, UnsupportedOperationException;
	AccountDO withdrawal(Long accountNumber, Integer pin, Double amount) throws IllegalArgumentException, UnsupportedOperationException;
	Double getBalance(Long accountNumber) throws IllegalArgumentException;
	Long processDebitCheck(Long accountNumber, Integer pin, Double amount, Integer type, String description) throws IllegalArgumentException, UnsupportedOperationException;
}
