package com.project.bank_service.entity;
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
}
