package com.project.bank_service.repository;

import com.project.bank_service.entity.LoanOffer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanOfferRepository extends JpaRepository<LoanOffer, Long> {
    List<LoanOffer> findByBankId(Long bankId);
}
