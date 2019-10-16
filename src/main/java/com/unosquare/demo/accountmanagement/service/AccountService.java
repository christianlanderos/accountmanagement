package com.unosquare.demo.accountmanagement.service;

import com.unosquare.demo.accountmanagement.model.AccountDO;
import com.unosquare.demo.accountmanagement.model.HolderDO;


public interface AccountService {

	AccountDO authenticate(Long ssn, Integer accountPin);
	AccountDO openAccount(HolderDO holderDO);
	AccountDO closeAccount(Long accountNumber);
	AccountDO deposit(Long accountNumber, Integer pin, Double amount);
	AccountDO withdrawal(Long accountNumber, Integer pin, Double amount);
	Double getBalance(Long accountNumber);
	Long processDebitCheck(Long accountNumber, Integer pin, Double amount, Integer type, String description);
}
