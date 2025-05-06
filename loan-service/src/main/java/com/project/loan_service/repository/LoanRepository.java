package com.project.loan_service.repository;

import com.project.loan_service.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByUserId(Long userId);

    List<Loan> findByAppliedBankId(Long bankId);


    List<Loan> findByNextEmiDate(LocalDate nextEmiDate);
}