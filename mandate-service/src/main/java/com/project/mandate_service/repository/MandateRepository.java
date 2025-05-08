package com.project.mandate_service.repository;

import com.project.mandate_service.entity.Mandate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MandateRepository extends JpaRepository<Mandate, Long> {
    List<Mandate> findByUserId(Long userId);
    List<Mandate> findByLoanId(Long loanId);

    Optional<Mandate> findByLoanIdAndUserId(Long loanId, Long userId);

    @Query("SELECT m FROM Mandate m WHERE m.status = 'APPROVED'")
    List<Mandate> findApprovedMandates();

    List<Mandate>findAllMandatesByBankAccountId(Long bankId);

    List<Mandate> findByLoanIdIn(List<Long> loanIds);
}