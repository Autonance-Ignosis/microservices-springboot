package com.project.bank_service.repository;

import com.project.bank_service.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

    // Find all accounts for a given bank
    List<BankAccount> findByBankId(Long bankId);

    // Find all accounts for a specific user
    List<BankAccount> findByUserId(Long userId);
}