package com.project.kyc_service.DAO;

import com.project.kyc_service.entity.KycRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KycRecordRepository extends JpaRepository<KycRecord, Long> {
    Optional<KycRecord> findByUserId(int userId);

    List<KycRecord> findByUserIdOrderByCreatedAtDesc(int userId);

    List<KycRecord> findAll();

    long countByStatusIgnoreCase(String status);

    @Query("SELECT k FROM KycRecord k WHERE k.status = 'PENDING'")
    List<KycRecord> getKycPendingRequest();


}
