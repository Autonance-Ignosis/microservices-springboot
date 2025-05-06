package com.project.bank_service.repository;
import com.project.bank_service.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {
    // Find a bank by its IFSC code
    Bank findByIfsc(String ifsc);
    Bank findByAdminUserId(Long adminUserId);

}
