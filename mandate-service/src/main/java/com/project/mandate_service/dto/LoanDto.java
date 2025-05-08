package com.project.mandate_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanDto {
    private Long id;
    private Long userId;
    private Long bankAccountId;
    private Double emi;
    private LocalDate nextEmiDate;
    private String status;  // e.g., "APPROVED"
    private Long appliedBankId;
}
