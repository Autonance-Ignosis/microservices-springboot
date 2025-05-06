package com.project.mandate_service.dto;

import lombok.Data;

@Data
public class MandateRequestDto {
    private Long loanId;
    private Long userId;
    private Long bankAccountId;
}