package com.unosquare.demo.accountmanagement.service;

import com.unosquare.demo.accountmanagement.model.AccountDO;
import com.unosquare.demo.accountmanagement.model.HolderDO;


/**
 * @author Landeros
 * Service for account management.
 */
public interface AccountService {

	/**
	 * Authenticates a user
	 * @param ssn User SSN
	 * @param accountPin User assigned PIN
	 * @return the account of the user or null if not found
	 * @throws IllegalArgumentException
	 */
	AccountDO authenticate(Long ssn, Integer accountPin) throws IllegalArgumentException;
	/**
	 * Opens a new account for new users
	 * @param holderDO Container for user data (SSN, firstName, lastName)
	 * @return The created account or null if there are  missing parameters
	 * @throws IllegalArgumentException
	 */
	AccountDO openAccount(HolderDO holderDO) throws IllegalArgumentException;
	/**
	 * Disables an account. The account must not be overdrwan
	 * @param accountNumber The account number for the account to close
	 * @return the closed account
	 * @throws IllegalArgumentException
	 * @throws UnsupportedOperationException
	 */
	AccountDO closeAccount(Long accountNumber) throws IllegalArgumentException, UnsupportedOperationException;
	/**
	 * Makes a deposit into the specified account
	 * @param accountNumber The account number
	 * @param pin the confirmation PIN
	 * @param amount the amount to deposit
	 * @return the updated account information
	 * @throws IllegalArgumentException
	 * @throws UnsupportedOperationException
	 */
	AccountDO deposit(Long accountNumber, Integer pin, Double amount) throws IllegalArgumentException, UnsupportedOperationException;
	/**
	 * Makes a withdrawal into the specified account
	 * @param accountNumber the account number
	 * @param pin the confirmation PIN
	 * @param amount the amount to withdraw
	 * @return the updated account information
	 * @throws IllegalArgumentException
	 * @throws UnsupportedOperationException
	 */
	AccountDO withdrawal(Long accountNumber, Integer pin, Double amount) throws IllegalArgumentException, UnsupportedOperationException;
	/**
	 * Retrieves the current balance and the current account information
	 * @param accountNumber the account number
	 * @return the current account information
	 * @throws IllegalArgumentException
	 */
	AccountDO getBalance(Long accountNumber) throws IllegalArgumentException;
	/**
	 * Process debit/check operations (invoked from external sources)
	 * @param accountNumber the account number
	 * @param pin the confirmation PIN
	 * @param amount the amount
	 * @param type the type 3 = debit, 4 = check
	 * @param description the operation description
	 * @return the generated transaction id
	 * @throws IllegalArgumentException
	 * @throws UnsupportedOperationException
	 */
	Long processDebitCheck(Long accountNumber, Integer pin, Double amount, Long type, String description) throws IllegalArgumentException, UnsupportedOperationException;
}
