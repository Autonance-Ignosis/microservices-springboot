package com.project.loan_service.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanEvent {
    private Long userId;
    private String status; // APPROVED / REJECTED
    private Double amount;
}
