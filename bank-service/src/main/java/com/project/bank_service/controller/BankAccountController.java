package com.project.bank_service.controller;

import com.project.bank_service.dto.DeductRequest;
import com.project.bank_service.entity.BankAccount;
import com.project.bank_service.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bank-accounts")
public class BankAccountController {

    private final BankAccountService bankAccountService;

    @Autowired
    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    // Get all bank accounts for a specific bank
    @GetMapping("/bank/{bankId}")
    public ResponseEntity<List<BankAccount>> getAccountsByBankId(@PathVariable Long bankId) {
        List<BankAccount> accounts = bankAccountService.getAccountsByBankId(bankId);
        if (accounts.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(accounts);
    }

    // Get all bank accounts for a specific user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BankAccount>> getAccountsByUserId(@PathVariable Long userId) {
        System.out.println("Fetching accounts for user ID: " + userId);

        List<BankAccount> accounts = bankAccountService.getAccountsByUserId(userId);
        return ResponseEntity.ok(accounts);
    }

    // Create a new bank account for the user
    @PostMapping
    public ResponseEntity<BankAccount> createBankAccount(@RequestBody BankAccount bankAccount) {
        BankAccount createdAccount = bankAccountService.createBankAccount(bankAccount);
        return ResponseEntity.ok(createdAccount);
    }

    // Update an existing bank account
    @PutMapping("/{id}")
    public ResponseEntity<BankAccount> updateBankAccount(@PathVariable Long id, @RequestBody BankAccount bankAccount) {
        BankAccount updatedAccount = bankAccountService.updateBankAccount(id, bankAccount);
        return updatedAccount != null ? ResponseEntity.ok(updatedAccount) : ResponseEntity.notFound().build();
    }


    @GetMapping("/{id}")
    public ResponseEntity<BankAccount> getBankAccountById(@PathVariable Long id) {
        Optional<BankAccount> account = bankAccountService.getBankAccountById(id);
        return account.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PostMapping("/{id}/deduct")
    public ResponseEntity<String> deduct(@PathVariable Long id, @RequestBody DeductRequest request) {
        return bankAccountService.deduct(id, request.getAmount());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<BankAccount> getBankById(
            @PathVariable Long id,
            @RequestParam(required = false) Long userId) {

        System.out.println("Requested by user: " + userId);

        List<BankAccount> accounts = bankAccountService.getAccountsByUserId(userId);

        Optional<BankAccount> account = accounts.stream()
                .filter(ac -> ac.getBankId().equals(id))
                .findFirst();

        return account.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{bankId}/{userId}")
    public ResponseEntity<?> getAccountsByBankIdAndUserId(@PathVariable Long bankId, @PathVariable Long userId) {
        List<BankAccount> accounts = bankAccountService.getBankAccountByBankIdAndUserId(bankId, userId);

        if (accounts.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No bank accounts found for bankId " + bankId + " and userId " + userId);
        }

        return ResponseEntity.ok(accounts);
    }


}