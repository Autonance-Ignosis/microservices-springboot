package com.project.kyc_service.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


@Entity
@Table(name = "kyc_records")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class KycRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int userId; // Foreign key to User Service

    private String panNumber;
    private String aadhaarNumber;

    private String panFileUrl;
    private String aadhaarFileUrl;
//    private String salarySlipUrl;

    private String status;

    private String remarks;

//    private Long createdAt = System.currentTimeMillis();

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}
