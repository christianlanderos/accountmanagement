package com.unosquare.demo.accountmanagement.exposition;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.unosquare.demo.accountmanagement.exposition.dto.AccountDTO;
import com.unosquare.demo.accountmanagement.exposition.dto.HolderDTO;
import com.unosquare.demo.accountmanagement.exposition.dto.ResponseDTO;
import com.unosquare.demo.accountmanagement.exposition.dto.TransactionDTO;
import com.unosquare.demo.accountmanagement.model.AccountDO;
import com.unosquare.demo.accountmanagement.model.HolderDO;
import com.unosquare.demo.accountmanagement.model.TransactionDO;
import com.unosquare.demo.accountmanagement.service.AccountService;
import com.unosquare.demo.accountmanagement.util.Constants;

@RestController
@RequestMapping(value="/account")
public class AccountRestController {

	@Autowired
	private AccountService accountService;

	@RequestMapping(value="/authentication", method=RequestMethod.POST)
	public ResponseDTO authenticate(Long ssn, Integer accountPin) {
	
		ResponseDTO responseDTO = new ResponseDTO();
		AccountDO accountDO = null;

		try {
		
			accountDO = accountService.authenticate( ssn, accountPin );
			if (accountDO == null)
				throw new IllegalArgumentException("Login failed.");
			
			AccountDTO accountDTO = mapDOToDTO( accountDO );
			responseDTO.setAccountDTO( accountDTO );
			responseDTO.setResponseCode( Constants.SUCCESS_CODE );
			responseDTO.setResponseMessage( Constants.GENERAL_SUCCESS_MESSAGE );
		} catch(IllegalArgumentException iae) {
			
			responseDTO.setResponseCode( Constants.ERROR_CODE );
			responseDTO.setResponseMessage( iae.getMessage() );
		}
		
		return responseDTO;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseDTO openAccount(HolderDTO holderDTO) {
		
		ResponseDTO responseDTO = new ResponseDTO();
		AccountDO accountDO = null;

		try {
		
			HolderDO holderDO = mapDTOToDO(holderDTO);
			accountDO = accountService.openAccount( holderDO );
			if (accountDO == null)
				throw new IllegalArgumentException("Account cannot be openned.");
			
			AccountDTO accountDTO = mapDOToDTO( accountDO );
			responseDTO.setAccountDTO( accountDTO );
			responseDTO.setResponseCode( Constants.SUCCESS_CODE );
			responseDTO.setResponseMessage( Constants.GENERAL_SUCCESS_MESSAGE );
		} catch(IllegalArgumentException iae) {
			
			responseDTO.setResponseCode( Constants.ERROR_CODE );
			responseDTO.setResponseMessage( iae.getMessage() );
		}
		
		return responseDTO;
	}
	
	@RequestMapping(method=RequestMethod.DELETE)
	public ResponseDTO closeAccount(Long accountNumber) {
		
		ResponseDTO responseDTO = new ResponseDTO();
		AccountDO accountDO = null;
		
		try {
			
			accountDO = accountService.closeAccount( accountNumber );
			if (accountDO == null)
				throw new IllegalArgumentException("Account not found.");
			AccountDTO accountDTO = mapDOToDTO( accountDO );
			responseDTO.setAccountDTO( accountDTO );
			responseDTO.setResponseCode( Constants.SUCCESS_CODE );
			responseDTO.setResponseMessage( Constants.GENERAL_SUCCESS_MESSAGE );
		} catch(IllegalArgumentException | UnsupportedOperationException e) {
			
			responseDTO.setResponseCode( Constants.ERROR_CODE );
			responseDTO.setResponseMessage( e.getMessage() );
		}
		
		return responseDTO;
	}

	@RequestMapping(value="/deposit", method=RequestMethod.PUT)
	public ResponseDTO deposit(Long accountNumber, Integer pin, Double amount) {
		
		ResponseDTO responseDTO = new ResponseDTO();
		AccountDO accountDO = null;
		
		try {
			
			accountDO = accountService.deposit( accountNumber, pin, amount );
			if (accountDO == null)
				throw new IllegalArgumentException("Account not found.");
			AccountDTO accountDTO = mapDOToDTO( accountDO );
			responseDTO.setAccountDTO( accountDTO );
			responseDTO.setResponseCode( Constants.SUCCESS_CODE );
			responseDTO.setResponseMessage( Constants.GENERAL_SUCCESS_MESSAGE );
		} catch(IllegalArgumentException | UnsupportedOperationException e) {
			
			responseDTO.setResponseCode( Constants.ERROR_CODE );
			responseDTO.setResponseMessage( e.getMessage() );
		}
		return responseDTO;
	}

	@RequestMapping(value="/withdrawal", method=RequestMethod.PUT)
	public ResponseDTO withdrawal(Long accountNumber, Integer pin, Double amount) {
		
		ResponseDTO responseDTO = new ResponseDTO();
		AccountDO accountDO = null;
		
		try {
			accountDO = accountService.withdrawal( accountNumber, pin, amount );
			if (accountDO == null)
				throw new IllegalArgumentException("Account not found.");
			
			AccountDTO accountDTO = mapDOToDTO( accountDO );
			responseDTO.setAccountDTO( accountDTO );
			responseDTO.setResponseCode( Constants.SUCCESS_CODE );
			responseDTO.setResponseMessage( Constants.GENERAL_SUCCESS_MESSAGE );
		} catch(IllegalArgumentException | UnsupportedOperationException e) {
			
			responseDTO.setResponseCode(Constants.ERROR_CODE);
			responseDTO.setResponseMessage(e.getMessage());
		}
		return responseDTO;
	}

	@RequestMapping(value="/account{id}", method=RequestMethod.GET)
	public ResponseDTO getBalance(@PathVariable(value="id") Long accountNumber) {
		
		ResponseDTO responseDTO = new ResponseDTO();
		
		try {

			Double balance = accountService.getBalance( accountNumber );
			AccountDTO accountDTO = new AccountDTO();
			accountDTO.setBalance( balance );
			responseDTO.setAccountDTO(accountDTO);
			responseDTO.setResponseCode( Constants.SUCCESS_CODE );
			responseDTO.setResponseMessage( Constants.GENERAL_SUCCESS_MESSAGE );
		} catch(IllegalArgumentException iae) {
			
			responseDTO.setResponseCode( Constants.ERROR_CODE );
			responseDTO.setResponseMessage( iae.getMessage() );
		}
		
		return responseDTO;
	}

	@RequestMapping(value="/process", method=RequestMethod.PUT)
	public Long processDebitCheck(Long accountNumber, Integer pin, Double amount, Integer type, String description) {
		
		Long transactionCode = null;
		
		transactionCode = accountService.processDebitCheck( accountNumber, pin, amount, type, description );
		
		return transactionCode;
	}

	private AccountDTO mapDOToDTO(AccountDO accountDO) {
		
		AccountDTO accountDTO = null;
		
		if ( accountDO != null ) {
			
			accountDTO = new AccountDTO();
			accountDTO.setBalance( accountDO.getBalance() );
			accountDTO.setNumber( accountDO.getNumber() );
			accountDTO.setPin( accountDO.getPin() );
			List<TransactionDO> transactionDOList = accountDO.getTransactionDOList();
			if ( transactionDOList != null ) {
				
				List<TransactionDTO> transactionDTOList = new ArrayList<>();
				int limit = 5;
				if (transactionDOList.size() < 5)
					limit = transactionDOList.size();
				for (int i=0; i<limit; i++) {
					
					TransactionDTO transactionDTO = mapDOToDTO( transactionDOList.get(i) );
					transactionDTOList.add( transactionDTO );
				}
				accountDTO.setTransactionDTOList( transactionDTOList );
			}
			HolderDO holderDO = accountDO.getHolderDO();
			if ( holderDO != null ) {
				
				HolderDTO holderDTO = mapDOToDTO( holderDO );
				accountDTO.setHolderDTO(holderDTO);
			}
		}
		
		return accountDTO;
	}
	
	private HolderDO mapDTOToDO(HolderDTO holderDTO) throws IllegalArgumentException {
		
		HolderDO holderDO = null;
		
		if ( holderDTO != null ) {
			
			holderDO = new HolderDO();
			holderDO.setSsn( holderDTO.getSsn() );
			holderDO.setFirstName( holderDTO.getFirstName() );
			holderDO.setLastName( holderDTO.getLastName() );
		}
		
		return holderDO;
	}
	
	private HolderDTO mapDOToDTO(HolderDO holderDO) {
		
		HolderDTO holderDTO = null;
		
		if ( holderDO != null ) {
		
			holderDTO = new HolderDTO();
			holderDTO.setSsn( holderDO.getSsn() );
			holderDTO.setFirstName( holderDO.getFirstName() );
			holderDTO.setLastName( holderDO.getLastName() );
		}
		return holderDTO;
	}
	
	private TransactionDTO mapDOToDTO(TransactionDO transactionDO) {
		
		TransactionDTO transactionDTO = null;
		
		if ( transactionDO != null ) {
			
			transactionDTO = new TransactionDTO();
			transactionDTO.setAmount( transactionDO.getAmount() );
			transactionDTO.setDate( transactionDO.getDate() );
			transactionDTO.setDescription( transactionDO.getDescription() );
			transactionDTO.setId( transactionDO.getId() );
			if ( transactionDO.getTransactionTypeDO() != null )
				transactionDTO.setTransactionType( transactionDO.getTransactionTypeDO().getName() );
		}
		
		return transactionDTO;
	}
}
