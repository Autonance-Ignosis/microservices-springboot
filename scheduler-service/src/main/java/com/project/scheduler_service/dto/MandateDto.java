package com.project.scheduler_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MandateDto {
    private Long id;
    private Long loanId;
    private Long userId;
    private Long bankAccountId;
    private String status; // PENDING, APPROVED, REJECTED
}