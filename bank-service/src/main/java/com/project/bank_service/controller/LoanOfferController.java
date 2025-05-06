package com.project.bank_service.controller;

import com.project.bank_service.entity.LoanOffer;
import com.project.bank_service.repository.LoanOfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loan-offers")
@RequiredArgsConstructor
public class LoanOfferController {

    private final LoanOfferRepository loanOfferRepository;

    @GetMapping
    public ResponseEntity<List<LoanOffer>> getAll() {
        return ResponseEntity.ok(loanOfferRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<LoanOffer> createLoanOffer(@RequestBody LoanOffer offer) {
        return ResponseEntity.ok(loanOfferRepository.save(offer));
    }

    @GetMapping("/bank/{bankId}")
    public ResponseEntity<List<LoanOffer>> getOffersByBank(@PathVariable Long bankId) {
        return ResponseEntity.ok(loanOfferRepository.findByBankId(bankId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanOffer> getById(@PathVariable Long id) {
        return ResponseEntity.of(loanOfferRepository.findById(id));
    }


    @GetMapping("/get-bank-id/{id}")
    public Long getBankIdByLoanOfferId(@PathVariable Long id) {
        return loanOfferRepository.findById(id).map(LoanOffer::getBankId)
                .orElseThrow(() -> new RuntimeException("Loan offer not found"));
    }

}
