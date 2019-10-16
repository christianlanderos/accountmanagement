package com.unosquare.demo.accountmanagement.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unosquare.demo.accountmanagement.model.AccountDO;
import com.unosquare.demo.accountmanagement.model.HolderDO;
import com.unosquare.demo.accountmanagement.model.TransactionDO;
import com.unosquare.demo.accountmanagement.model.TransactionTypeDO;
import com.unosquare.demo.accountmanagement.repository.AccountRepository;
import com.unosquare.demo.accountmanagement.repository.BalanceRepository;
import com.unosquare.demo.accountmanagement.repository.TransactionRepository;
import com.unosquare.demo.accountmanagement.service.AccountService;
import com.unosquare.demo.accountmanagement.util.Constants;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private BalanceRepository balanceRepository;

	
	
	@Override
	public AccountDO authenticate(Long ssn, Integer accountPin) throws IllegalArgumentException {
		
		AccountDO accountDO = null;
		
		if ( ssn==null || accountPin==null ) 
			throw new IllegalArgumentException("Values for ssn and account pint must not be null.");
		
		accountDO = accountRepository.findByHolderDO_ssnAndPin(ssn, accountPin);
		
		return accountDO;
	}

	@Transactional
	@Override
	public AccountDO openAccount(HolderDO holderDO) throws IllegalArgumentException {
		
		AccountDO createdAccountDO = null;
		
		if ( !validateHolderDO( holderDO ) )
			throw new IllegalArgumentException("First name and last name must not be null or empty.");
		
		AccountDO accountDO = new AccountDO();
		accountDO.setNumber( this.generateAccountNumber() );
		accountDO.setPin( this.generateAccountPin() );
		accountDO.setHolderDO( holderDO );
		accountDO.setBalance( 0D );
		accountDO.setEnabled( Boolean.TRUE );
		accountDO.setTransactionDOList( new ArrayList<TransactionDO>() );
		createdAccountDO = accountRepository.save( accountDO );
		
		return createdAccountDO;
	}

	@Transactional
	@Override
	public AccountDO closeAccount(Long accountNumber) 
			throws IllegalArgumentException, UnsupportedOperationException {
		
		AccountDO accountDO = null;
		
		if (accountNumber == null)
			throw new IllegalArgumentException("Account number must not be null.");
		
		accountDO = accountRepository.getOne( accountNumber );
		if ( accountDO != null ) {
			
			if ( accountDO.getBalance() < 0D )
				throw new UnsupportedOperationException("The account is overdrawn, therefore it cannot be closed.");
			
			accountDO.setEnabled( Boolean.FALSE );
			accountRepository.save( accountDO );
		}
		
		return accountDO;
	}

	@Transactional
	@Override
	public AccountDO deposit(Long accountNumber, Integer accountPin, Double amount)
			throws IllegalArgumentException, UnsupportedOperationException {
		
		AccountDO updatedAccountDO = null;
		String description = "Deposit into account";
		
		validateTransactionParams(accountNumber, accountPin, amount);
		
		updatedAccountDO = processTransaction(accountNumber, accountPin, amount, Constants.TRASACTION_TYPE_DEPOSIT, description);
		
		return updatedAccountDO;
	}

	@Transactional
	@Override
	public AccountDO withdrawal(Long accountNumber, Integer accountPin, Double amount)
			throws IllegalArgumentException, UnsupportedOperationException {

		AccountDO updatedAccountDO = null;
		String description = "Withdrawal from account";
		
		validateTransactionParams(accountNumber, accountPin, amount);
		
		updatedAccountDO = processTransaction(accountNumber, accountPin, amount, Constants.TRASACTION_TYPE_WITHDRAWAL, description);
		
		return updatedAccountDO;
	}

	@Override
	public Double getBalance(Long accountNumber) throws IllegalArgumentException {
		
		Double currentBalance = null;
		
		if (accountNumber == null)
			throw new IllegalArgumentException("Account number must not be null.");
		
		currentBalance = balanceRepository.getBalance( accountNumber );
		
		return currentBalance;
	}

	@Transactional
	@Override
	public Long processDebitCheck(Long accountNumber, Integer accountPin, Double amount, Integer type,
			String description) throws IllegalArgumentException, UnsupportedOperationException {
		
		AccountDO updatedAccountDO = null;
		Long transactionId = null;
		
		validateTransactionParams(accountNumber, accountPin, amount);
		if ( type == null || type < 3 || type > 4 )
			throw new IllegalArgumentException("Type must not be null and must be a value of 3 or 4 (3=Debit, 4=Check).");
		if ( description == null || description.trim().equals(Constants.EMPTY_STRING) )
			throw new IllegalArgumentException("Description must not be null or empty.");
		
		updatedAccountDO = processTransaction(accountNumber, accountPin, amount, Constants.TRASACTION_TYPE_WITHDRAWAL, description);
		if ( updatedAccountDO != null && 
			 updatedAccountDO.getTransactionDOList() != null && 
			 updatedAccountDO.getTransactionDOList().get(0) != null ) {
		
			transactionId = updatedAccountDO.getTransactionDOList().get(0).getId();
		}
		return transactionId;
	}
	
	@Transactional
	private synchronized AccountDO processTransaction(Long accountNumber, Integer accountPin,
			Double amount, int type, String description) throws UnsupportedOperationException {
		
		AccountDO updatedAccountDO = null;
		Double currentBalance = null;
		
		AccountDO retreivedAccountDO = accountRepository.getOne( accountNumber );
		if ( retreivedAccountDO == null )
			throw new UnsupportedOperationException("Account not found.");
		
		if ( retreivedAccountDO.getPin() != accountPin )
			throw new UnsupportedOperationException("Forbidden transaction. Invalid PIN.");
		
		if ( type == Constants.TRASACTION_TYPE_WITHDRAWAL || 
				type == Constants.TRASACTION_TYPE_DEBIT ) {
		
			currentBalance = retreivedAccountDO.getBalance();
			if ( currentBalance < 0D )
				throw new UnsupportedOperationException("Forbidden transaction. The account is overdrawn.");
			if ( currentBalance < amount ) 
				throw new UnsupportedOperationException("Forbidden transaction. Not enough funds.");
			amount *= -1;
		}
		
		TransactionDO transactionDO = new TransactionDO();
		transactionDO.setAmount( amount );
		transactionDO.setDate( new Date() );
		transactionDO.setDescription( description );
		
		TransactionTypeDO transactionTypeDO = new TransactionTypeDO();
		transactionTypeDO.setId( type );
		transactionDO.setTransactionTypeDO( transactionTypeDO );
		
		AccountDO accountDO = new AccountDO();
		accountDO.setNumber( accountNumber );
		transactionDO.setAccountDO( accountDO );
		
		transactionRepository.save( transactionDO );
		
		currentBalance = balanceRepository.getBalance( accountNumber );
		retreivedAccountDO.setBalance( currentBalance );
		updatedAccountDO = accountRepository.save( retreivedAccountDO );
	
		return updatedAccountDO;
	}
	
	private Long generateAccountNumber() {
		
		Long accountNumber = null;
		
		accountNumber = getRandomNumber( Constants.MIN_ACCOUNT_VALUE, Constants.MAX_ACCOUNT_VALUE );
		
		return accountNumber;
	}
	
	private Integer generateAccountPin() {
		
		Long accountPin = null;
		
		accountPin = getRandomNumber( Constants.MIN_PIN_VALUE, Constants.MAX_PIN_VALUE );
		
		return accountPin.intValue();
	}
	
	private Long getRandomNumber(long low, long high) {
		
		Long number = null;
		
		number = ThreadLocalRandom.current().nextLong(low, high+1);
		
		return number;
	}
	
	private boolean validateHolderDO(HolderDO holderDO) {
		
		if ( holderDO == null )
			return false;
		
		if ( holderDO.getFirstName() == null || 
			 holderDO.getLastName() == null ||
			 holderDO.getFirstName().trim().equals(Constants.EMPTY_STRING) || 
			 holderDO.getLastName().trim().equals(Constants.EMPTY_STRING) )
			return false;
		
		return true;
	}
	
	private void validateTransactionParams(Long accountNumber, Integer accountPin, Double amount)
			throws IllegalArgumentException {
		
		if ( accountNumber == null )
			throw new IllegalArgumentException("Account number must not be null");
		if ( accountPin == null )
			throw new IllegalArgumentException("Account pin must not be null");
		if ( amount == null || amount <= 0D )
			throw new IllegalArgumentException("Amount must not be null and must be a positive value.");
	}
	
}
