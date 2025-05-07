package com.project.mandate_service.entity;

import com.project.mandate_service.enums.*;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mandates")
public class Mandate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "loan_id", nullable = false)
    private Long loanId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "bank_account_id", nullable = false)
    private Long bankAccountId;

    @Column(name = "status")
    private String status = "PENDING"; // PENDING, APPROVED, REJECTED

    // Mandate Details
    @Enumerated(EnumType.STRING)
    @Column(name = "mandate_variant")
    private MandateVariant mandateVariant;
//    private String mandateVariant;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private CategoryType category;

    @Enumerated(EnumType.STRING)
    @Column(name = "debit_type")
    private DebitType debitType;

    @Enumerated(EnumType.STRING)
    @Column(name = "seq_type")
    private SequenceType seqType;

    @Enumerated(EnumType.STRING)
    @Column(name = "freq_type")
    private FrequencyType freqType;

    private String schemaName;

    @Column(name = "cons_ref_no")
    private String consRefNo;

    private Double amount;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "upto_date")
    private String uptoDate;

    @Column(name = "up_to_40_years")
    private Boolean upTo40Years = false;


    private Long createdAt = System.currentTimeMillis();

    private Long updatedAt = System.currentTimeMillis();

    // Prediction-related fields
    @Column(name = "prediction_status")
    private String predictionStatus; // APPROVED or REJECTED based on model prediction

    @Column(name = "prediction_probability")
    private Double predictionProbability; // Probability score of the prediction (optional)
}
