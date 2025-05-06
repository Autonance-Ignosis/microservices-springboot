package com.project.bank_service.service;

import com.project.bank_service.entity.Bank;
import com.project.bank_service.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BankService {

    private final BankRepository bankRepository;

    @Autowired
    public BankService(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    public List<Bank> getAllBanks() {
        return bankRepository.findAll();
    }

    public Bank getBankByIfsc(String ifsc) {
        return bankRepository.findByIfsc(ifsc);
    }

    public Bank createBank(Bank bank) {
        return bankRepository.save(bank);
    }

    public Optional<Bank> getBankById(Long id) {
        return bankRepository.findById(id);
    }

    public Bank updateBank(Long id, Bank updatedBank) {
        Optional<Bank> existingBank = bankRepository.findById(id);
        if (existingBank.isPresent()) {
            Bank bank = existingBank.get();
            bank.setName(updatedBank.getName());
            bank.setIfsc(updatedBank.getIfsc());
            bank.setAdminUserId(updatedBank.getAdminUserId());
            return bankRepository.save(bank);
        }
        return null;
    }

    public Bank findBankByAdminUserId(Long adminUserId) {
        return bankRepository.findByAdminUserId(adminUserId);
    }
}