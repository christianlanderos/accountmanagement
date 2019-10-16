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
	public AccountDO closeAccount(Long accountNumber) throws IllegalArgumentException {
		
		AccountDO accountDO = null;
		
		if (accountNumber == null)
			throw new IllegalArgumentException("Account number must not be null.");
		
		accountDO = accountRepository.getOne( accountNumber );
		if ( accountDO != null ) {
			
			accountDO.setEnabled( Boolean.FALSE );
			accountRepository.save( accountDO );
		}
		
		return accountDO;
	}

	@Transactional
	@Override
	public AccountDO deposit(Long accountNumber, Double amount) {
		
		AccountDO updatedAccountDO = null;
		String description = "Deposit into account";
		
		if ( accountNumber == null )
			throw new IllegalArgumentException("Account number must not be null");
		if ( amount == null || amount <= 0D )
			throw new IllegalArgumentException("Amount must not be null and must be a positive value.");
		
		updatedAccountDO = processTransaction(accountNumber, amount, Constants.TRASACTION_TYPE_DEPOSIT, description);
		
		return updatedAccountDO;
	}

	@Transactional
	@Override
	public AccountDO withdrawal(Long accountNumber, Double amount) {

		AccountDO updatedAccountDO = null;
		String description = "Withdrawal from account";
		
		if ( accountNumber == null )
			throw new IllegalArgumentException("Account number must not be null");
		if ( amount == null || amount <= 0D )
			throw new IllegalArgumentException("Amount must not be null and must be a positive value.");
		
		updatedAccountDO = processTransaction(accountNumber, amount, Constants.TRASACTION_TYPE_WITHDRAWAL, description);
		
		return updatedAccountDO;
	}

	@Override
	public Double getBalance(Long accountNumber) {
		
		Double currentBalance = null;
		
		if (accountNumber == null)
			throw new IllegalArgumentException("Account number must not be null.");
		
		currentBalance = balanceRepository.getBalance( accountNumber );
		
		return currentBalance;
	}

	@Transactional
	@Override
	public Long processDebitCheck(Long accountNumber, Double amount, Integer type, String description) {
		
		if ( accountNumber == null )
			throw new IllegalArgumentException("Account number must not be null");
		if ( amount == null || amount <= 0D )
			throw new IllegalArgumentException("Amount must not be null and must be a positive value.");
		if ( type == null || type < 3 || type > 4 )
			throw new IllegalArgumentException("Type must not be null and must be a value of 3 or 4 (3=Debit, 4=Check).");
		if ( description == null || description.trim().equals(Constants.EMPTY_STRING) )
			throw new IllegalArgumentException("Description must not be null or empty.");
		processTransaction(accountNumber, amount, Constants.TRASACTION_TYPE_WITHDRAWAL, description);
		
		return null;
	}
	
	private synchronized AccountDO processTransaction(Long accountNumber, Double amount, int type, String description) {
		
		AccountDO updatedAccountDO = null;
		Double currentBalance = null;
		
		if (type == Constants.TRASACTION_TYPE_WITHDRAWAL || type == Constants.TRASACTION_TYPE_DEBIT) {
		
			currentBalance = balanceRepository.getBalance( accountNumber );
			if ( currentBalance < amount ) 
				throw new UnsupportedOperationException("Account without enough funds");
		}
		
		TransactionDO transactionDO = new TransactionDO();
		transactionDO.setAmount( -1 * amount );
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
		AccountDO retreivedAccountDO = accountRepository.getOne( accountNumber );
		if ( retreivedAccountDO != null ) {
			
			retreivedAccountDO.setBalance( currentBalance );
			updatedAccountDO = accountRepository.save( retreivedAccountDO );
		}
		
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
}
