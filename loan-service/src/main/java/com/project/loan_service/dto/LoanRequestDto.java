package com.project.loan_service.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanRequestDto {

    private Long userId;
    private Long bankAccountId;

    private Long loanOfferId;
    private Long appliedBankId;
    private Double amount;
    private Integer tenureInMonths;
    private Double interestRate;

    // ML Features (input to model)
    private Boolean creditCriteriaMet;
    private String purpose;
    private Double logIncome;
    private Double debtIncomeRatio;
    private Integer ficoScore;
    private Integer daysWithCreditLine;
    private Double revolvingBalance;
    private Double revolvingUtilization;
    private Integer inquiryLast6Months;
    private Integer timesLateIn2Years;
    private Integer derogatoryPublicRecords;
}
