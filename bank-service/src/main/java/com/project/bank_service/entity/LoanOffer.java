package com.project.bank_service.entity;
import com.project.bank_service.enums.LoanType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "loan_offers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long bankId; // Foreign key to Bank

    private String offerName; // e.g., "Personal Loan", "Home Loan"

    private Double interestRate;

    private Integer maxTenureInMonths;

    private Double maxAmount;

    private String description; // Optional: Extra info about the offer

    @Enumerated(EnumType.STRING)
    private LoanType loanType; // Enum for different types of loans

    private String termsAndConditions; // Optional: Terms and conditions for the loan offer

    private String eligibilityCriteria; // Optional: Eligibility criteria for the loan offer

    private String applicationProcess; // Optional: How to apply for the loan

    private String contactDetails; // Optional: Contact details for inquiries

    @Column(name = "created_at", updatable = false)
    private java.time.LocalDateTime createdAt;

    @Column(name = "updated_at")
    private java.time.LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = java.time.LocalDateTime.now();
        this.updatedAt = java.time.LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = java.time.LocalDateTime.now();
    }
}
