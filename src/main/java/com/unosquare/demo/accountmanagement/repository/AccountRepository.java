package com.unosquare.demo.accountmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.unosquare.demo.accountmanagement.model.AccountDO;

@Repository
public interface AccountRepository extends JpaRepository<AccountDO, Long> {

	AccountDO findByHolderDO_ssnAndPin(Long ssn, Integer accountPin);
	
	//@Query(value="SELECT * from accounts a LEFT JOIN (SELECT TOP 5 * FROM transactions t ORDER BY t.id DESC) t2 ON a.number=t2.number WHERE a.number=?1", nativeQuery=true)
	//AccountDO findAccountLimitedToFiveTransactions(Long accountNumber);
	
}
