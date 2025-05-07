package com.project.mandate_service.dto;

import com.project.mandate_service.enums.*;

import lombok.Data;

@Data
public class MandateRequestDto {
    private Long loanId;
    private Long userId;
    private Long bankAccountId;

    // Mandate Details
    private MandateVariant mandateVariant;
    //    private String mandateVariant;
    private CategoryType category;
    private DebitType debitType;
    private SequenceType seqType;
    private FrequencyType freqType;
    private String schemaName;
    private String consRefNo;
    private Double amount;
    private String startDate;
    private String uptoDate;
    private Boolean upTo40Years;


}
