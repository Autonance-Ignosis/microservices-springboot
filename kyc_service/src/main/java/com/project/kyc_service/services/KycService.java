package com.project.kyc_service.services;

import com.project.kyc_service.DAO.KycRecordRepository;
import com.project.kyc_service.clients.UserClient;
import com.project.kyc_service.entity.KycRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KycService {

    private final KycRecordRepository repository;

    private final UserClient userClient;

    public KycRecord uploadKyc(int userId, String panNo, String aadhaarNo,
                               String panFileUrl, String aadhaarFileUrl) throws IOException {

//        String panUrl = cloudinaryService.uploadFile(panFile, "kyc/pan");
//        String aadhaarUrl = cloudinaryService.uploadFile(aadhaarFile, "kyc/aadhaar");

        KycRecord record = new KycRecord();
        record.setUserId(userId);
        record.setPanNumber(panNo);
        record.setAadhaarNumber(aadhaarNo);
        record.setPanFileUrl(panFileUrl);
        record.setAadhaarFileUrl(aadhaarFileUrl);
        record.setStatus("PENDING");
        record.setRemarks("Awaiting verification");

        return repository.save(record);
    }

    public Optional<KycRecord> getStatusByUserId(int userId) {
        System.out.println("Fetching KYC for userId: " + userId);
        return repository.findByUserId(userId);
    }



    public List<KycRecord> getKycRecordsByUserId(int userId) {
        return repository.findByUserIdOrderByCreatedAtDesc(userId);
    }


    public KycRecord updateStatus(int userId, String status, String remarks) {

        List<KycRecord> records = getKycRecordsByUserId(userId);

        if (records.isEmpty()) {
            throw new RuntimeException("No KYC records found for user ID: " + userId);
        }

        // Get the latest KYC record
        // Assuming the latest record is the first one in
        KycRecord record = records.get(0);

        record.setStatus(status);
        record.setRemarks(remarks);
        repository.save(record);

        // 🔁 Update KYC status in USER-SERVICE via Feign
        userClient.updateKycStatus(userId, status);

        return record;
    }

    public List<KycRecord> getKycStatus(){
        List<KycRecord> records = repository.findAll();
        if(records.isEmpty()){
            throw new RuntimeException("No KYC records found");
        }
        return records;
    }


}
