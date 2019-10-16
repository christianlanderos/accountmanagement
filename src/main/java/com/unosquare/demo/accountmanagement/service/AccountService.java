package com.unosquare.demo.accountmanagement.service;

import com.unosquare.demo.accountmanagement.model.AccountDO;
import com.unosquare.demo.accountmanagement.model.HolderDO;


public interface AccountService {

	AccountDO authenticate(Long ssn, Integer accountPin);
	AccountDO openAccount(HolderDO holderDO);
	AccountDO closeAccount(Long accountNumber);
	AccountDO deposit(Long accountNumber, Double amount);
	AccountDO withdrawal(Long accountNumber, Double amount);
	Double getBalance(Long accountNumber);
	Long processDebitCheck(Long accountNumber, Double amount, Integer type, String description);
}
