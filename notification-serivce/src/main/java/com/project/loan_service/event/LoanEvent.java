package com.project.loan_service.event;

import lombok.Data;

@Data
public class LoanEvent {
    private Long userId;
    private String status;
    private Long loanId;
    private Long amount;
}