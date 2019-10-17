package com.unosquare.demo.accountmanagement.service;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import com.unosquare.demo.accountmanagement.model.AccountDO;
import com.unosquare.demo.accountmanagement.model.HolderDO;
import com.unosquare.demo.accountmanagement.model.TransactionDO;
import com.unosquare.demo.accountmanagement.model.TransactionTypeDO;
import com.unosquare.demo.accountmanagement.repository.AccountRepository;
import com.unosquare.demo.accountmanagement.repository.TransactionRepository;
import com.unosquare.demo.accountmanagement.service.impl.AccountServiceImpl;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class AccountServiceTest {

	
	@Mock
	private TransactionRepository transactionRepository;
	@Mock
	private AccountRepository accountRepository;
	
	@InjectMocks
	private AccountService accountService = new AccountServiceImpl();
	
	@Before
    public void init() {

		MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void testAuthenticate_happyPath() {
		
		List<TransactionDO> transactionDOList = new ArrayList<>();
		TransactionDO transactionDO = new TransactionDO();
		transactionDO.setAmount(1000D);
		transactionDO.setDate(new Date());
		transactionDO.setDescription("Test");
		transactionDO.setId(1L);
		AccountDO accountDO = new AccountDO();
		accountDO.setBalance(3000D);
		accountDO.setEnabled(true);
		accountDO.setNumber(11111111111L);
		accountDO.setPin(1111);
		accountDO.setTransactionDOList(transactionDOList);
		HolderDO holderDO = new HolderDO();
		holderDO.setSsn(12345678901L);
		holderDO.setFirstName("Christian");
		holderDO.setLastName("Landeros");
		accountDO.setHolderDO(holderDO);
		transactionDO.setAccountDO(accountDO);
		TransactionTypeDO transactionTypeDO = new TransactionTypeDO();
		transactionTypeDO.setId(1L);
		transactionTypeDO.setName("Deposit");
		transactionTypeDO.setDescription("Test");
		transactionDO.setTransactionTypeDO(transactionTypeDO);
		transactionDOList.add(transactionDO);
		
		when(accountRepository.findByHolderDO_ssnAndPin(Mockito.anyLong(), Mockito.anyInt())).thenReturn(accountDO);
		
        when(transactionRepository.findTop5ByAccountDO_numberOrderByIdDesc(Mockito.anyLong())).thenReturn(transactionDOList);
        
        
        AccountDO retrievedAccountDO = accountService.authenticate(1L, 1111);
        Assert.notNull(retrievedAccountDO, "Null Response");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testAuthenticate_nullSsn() {
		
		AccountDO retrievedAccountDO = accountService.authenticate(null, 1111);
    }
	
	@Test(expected=IllegalArgumentException.class)
	public void testAuthenticate_nullPin() {
		
		AccountDO retrievedAccountDO = accountService.authenticate(1L, null);
    }
	
	@Test(expected=IllegalArgumentException.class)
	public void testAuthenticate_acountNotFound() {
		
		when(accountRepository.findByHolderDO_ssnAndPin(Mockito.anyLong(), Mockito.anyInt())).thenReturn(null);
		
        AccountDO retrievedAccountDO = accountService.authenticate(1L, 1111);
	}
}
