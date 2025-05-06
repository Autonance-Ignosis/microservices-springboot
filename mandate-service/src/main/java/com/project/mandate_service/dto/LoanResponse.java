package com.project.mandate_service.dto;

import lombok.Data;

@Data
public class LoanResponse {
    private Long id;
    private Long userId;
    private String status;  // e.g., "APPROVED"
}