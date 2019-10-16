package com.unosquare.demo.accountmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unosquare.demo.accountmanagement.model.AccountDO;

@Repository
public interface AccountRepository extends JpaRepository<AccountDO, Long> {

	AccountDO findByHolderDO_ssnAndPin(Long ssn, Integer accountPin);
	
}
