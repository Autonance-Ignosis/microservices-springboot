package com.project.loan_service.controller;

import com.project.loan_service.dto.LoanDto;
import com.project.loan_service.dto.LoanRequestDto;
import com.project.loan_service.entity.Loan;
import com.project.loan_service.service.LoanService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }



    @GetMapping
    public List<Loan> getAllLoans() {
        return loanService.getAllLoans();
    }

    @PostMapping
    public Loan applyLoan(@RequestBody LoanRequestDto dto) {
        return loanService.applyLoan(dto);
    }

    @GetMapping("/{id}")
    public Loan getLoan(@PathVariable Long id) {
        return loanService.getLoanById(id);
    }

    @GetMapping("/user/{userId}")
    public List<Loan> getLoansByUser(@PathVariable Long userId) {
        return loanService.getLoansByUserId(userId);
    }

    @PutMapping("/{loanId}/approve")
    public Loan approveLoan(@PathVariable Long loanId) {
        return loanService.approveLoan(loanId);
    }

    @PutMapping("/{loanId}/reject")
    public Loan rejectLoan(@PathVariable Long loanId) {
        return loanService.rejectLoan(loanId);
    }


    @GetMapping("/bank/{bankId}")
    public List<Loan> getLoansByBank(@PathVariable Long bankId) {
        return loanService.getLoansByBankId(bankId);
    }

    @GetMapping("/due-today")
    public List<LoanDto> getLoansDueToday() {
        return loanService.getLoansDueToday();
    }

}
