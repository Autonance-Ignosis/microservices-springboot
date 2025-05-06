package com.project.loan_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PredictionRequest {
    private double interest_rate;
    private double installment;
    private double log_of_income;
    private double debt_income_ratio;
    private double fico_score;
    private double days_with_credit_line;
    private double revolving_balance;
    private double revolving_utilization;
    private int credit_criteria_meet;
    private int inquiry_last_6months;
    private int times_surpassed_payment_in_2yrs;
    private int derogatory_public_record;

    private boolean purpose_all_other;
    private boolean purpose_credit_card;
    private boolean purpose_debt_consolidation;
    private boolean purpose_educational;
    private boolean purpose_home_improvement;
    private boolean purpose_major_purchase;
    private boolean purpose_small_business;
}
