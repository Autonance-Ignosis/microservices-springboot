package com.project.loan_service.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
@Entity
@Table(name = "loans")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "bank_account_id", nullable = true)
    private Long bankAccountId;

    @Column(name = "loan_offer_id", nullable = false)
    private Long loanOfferId;

    @Column(name = "applied_bank_id", nullable = false)
    private Long appliedBankId;


    @Column(name = "applied_at")
    private LocalDate appliedAt;

    @Column(name = "approved_at")
    private LocalDate approvedAt;


    private Double amount;

    private Integer tenureInMonths;

    private Double interestRate;

    private Double emi;

    @Column(name = "next_emi_date")
    private LocalDate nextEmiDate;

    @Enumerated(EnumType.STRING)
    private LoanStatus status;

    public enum LoanStatus {
        PENDING, APPROVED, REJECTED
    }

    // 🔽 ML features (collected/calculated during application)
    private Boolean creditCriteriaMet;

    private String purpose; // education, credit_card, etc.

    private Double logIncome;

    private Double debtIncomeRatio;

    private Integer ficoScore;

    private Integer daysWithCreditLine;

    private Double revolvingBalance;

    private Double revolvingUtilization;

    private Integer inquiryLast6Months;

    private Integer timesLateIn2Years;

    private Integer derogatoryPublicRecords;

    // 🔽 ML output
    private Double defaultRiskProbability; // e.g., 0.78 means 78% default risk
}
