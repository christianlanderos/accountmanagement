package com.unosquare.demo.accountmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unosquare.demo.accountmanagement.model.TransactionDO;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionDO, Long> {

	List<TransactionDO> findByAccountDO_number(Long number);
}
