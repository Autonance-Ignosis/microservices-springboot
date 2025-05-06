package com.project.bank_service.controller;

import com.project.bank_service.entity.Bank;
import com.project.bank_service.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/banks")
public class BankController {

    private final BankService bankService;

    @Autowired
    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    // Get all banks
    @GetMapping
    public ResponseEntity<List<Bank>> getAllBanks() {
        List<Bank> banks = bankService.getAllBanks();
        return ResponseEntity.ok(banks);
    }

    // Get a bank by IFSC
    @GetMapping("/{ifsc}")
    public ResponseEntity<Bank> getBankByIfsc(@PathVariable String ifsc) {
        Bank bank = bankService.getBankByIfsc(ifsc);
        return bank != null ? ResponseEntity.ok(bank) : ResponseEntity.notFound().build();
    }

    // Create a new bank
    @PostMapping
    public ResponseEntity<Bank> createBank(@RequestBody Bank bank) {
        Bank createdBank = bankService.createBank(bank);
        return ResponseEntity.ok(createdBank);
    }

    // Get a bank by ID
    @GetMapping("/id/{id}")
    public ResponseEntity<Bank> getBankById(@PathVariable Long id) {
        return bankService.getBankById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update an existing bank
    @PutMapping("/{id}")
    public ResponseEntity<Bank> updateBank(@PathVariable Long id, @RequestBody Bank bank) {
        Bank updatedBank = bankService.updateBank(id, bank);
        return updatedBank != null ? ResponseEntity.ok(updatedBank) : ResponseEntity.notFound().build();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Bank> getBankByUserId(@PathVariable Long id) {
        Bank bank=bankService.findBankByAdminUserId(id);
        return bank != null ? ResponseEntity.ok(bank) : ResponseEntity.notFound().build();
    }
}