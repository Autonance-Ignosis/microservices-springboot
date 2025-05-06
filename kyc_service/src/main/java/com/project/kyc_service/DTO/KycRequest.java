package com.project.kyc_service.DTO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class KycRequest {
    private int userId;
    private String panNumber;
    private String aadhaarNumber;
    private MultipartFile panFile;
    private MultipartFile aadhaarFile;
}
