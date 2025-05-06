package com.project.bank_service.entity;

import jakarta.persistence.*;

import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "bank_accounts")
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId; // The user who owns the account (from User service)

    @Column(name = "bank_id")
    private Long bankId; // Linked bank ID (from Bank service)

    private String accountNo; // Account number (encrypted/masked for security)

    @Column(name = "salary_source")
    private Boolean salarySource; // Is it a salary account?

    private Boolean verified; // Account verification status


    private Double balance;

    @Override
    public String toString() {
        return "BankAccount{" +
                "id=" + id +
                ", userId=" + userId +
                ", bankId=" + bankId +
                ", accountNo='" + accountNo + '\'' +
                ", salarySource=" + salarySource +
                ", verified=" + verified +
                '}';
    }

}