package com.project.scheduler_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeductRequest {

    private Long userId;
    private Long bankAccountId;
    private Double emi;
    private Long loanId;
}
