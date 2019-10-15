package com.unosquare.demo.accountmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.unosquare.demo.accountmanagement.model.TransactionDO;

@Repository
public interface BalanceRepository extends JpaRepository<TransactionDO, Long>  {

	@Query("SELECT SUM(amount) FROM TransactionDO t WHERE t.accountDO.number = ?1")
	Double getBalance(Long number);
}
