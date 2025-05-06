package com.project.bank_service.service;

import com.project.bank_service.entity.BankAccount;
import com.project.bank_service.repository.BankAccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;

    @Autowired
    public BankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    public List<BankAccount> getAccountsByBankId(Long bankId) {
        return bankAccountRepository.findByBankId(bankId);
    }

    public List<BankAccount> getAccountsByUserId(Long userId) {
        return bankAccountRepository.findByUserId(userId);
    }

    public BankAccount createBankAccount(BankAccount bankAccount) {
        return bankAccountRepository.save(bankAccount);
    }

    public Optional<BankAccount> getBankAccountById(Long id) {
        return bankAccountRepository.findById(id);
    }

    public BankAccount updateBankAccount(Long id, BankAccount updatedBankAccount) {
        Optional<BankAccount> existingAccount = bankAccountRepository.findById(id);
        if (existingAccount.isPresent()) {
            BankAccount account = existingAccount.get();
            account.setAccountNo(updatedBankAccount.getAccountNo());
            account.setSalarySource(updatedBankAccount.getSalarySource());
            account.setVerified(updatedBankAccount.getVerified());
            return bankAccountRepository.save(account);
        }
        return null;
    }

    public ResponseEntity<String> deduct(Long accountId, Double amount) {
        BankAccount account = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (account.getBalance() >= amount) {
            account.setBalance(account.getBalance() - amount);
            bankAccountRepository.save(account);
            return ResponseEntity.ok("Deducted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient balance");
        }
    }

    public List<BankAccount> getBankAccountByBankIdAndUserId(Long bankId, Long userId) {
        return bankAccountRepository.findByBankIdAndUserId(bankId, userId);
    }



}