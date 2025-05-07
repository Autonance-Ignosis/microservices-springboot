package com.project.bank_service.controller;

import com.project.bank_service.entity.Bank;
import com.project.bank_service.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//@CrossOrigin(origins = "http://localhost:5173")
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

    @PostMapping("/by-ids")
    public ResponseEntity<List<Bank>> getBanksByIds(@RequestBody List<Long> ids) {
        List<Bank> banks = new ArrayList<>();

        for (Long id : ids) {
            Optional<Bank> bankOpt = bankService.getBankById(id);
            if (bankOpt.isPresent()) {
                banks.add(bankOpt.get());
            }
        }
        return ResponseEntity.ok(banks);
    }


    @GetMapping("/id/{bankId}/{userId}")
    public Bank getBank(@PathVariable Long bankId, @PathVariable Long userId) {
        Bank bank = bankService.findBankByBankIdUserId(bankId, userId);
        if (bank == null) {
            System.out.println("No matching bank found for id=" + bankId + " and userId=" + userId);
        } else {
            System.out.println("✅ Found bank: " + bank);
        }
        return bank;
    }

}