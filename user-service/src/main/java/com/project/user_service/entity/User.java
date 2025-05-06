package com.project.user_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String fullName;

    @Column(unique = true, nullable = false)
    private String email;

    private String picture;

    private String role = "USER";

    private String kycStatus = "PENDING";

    private boolean flaggedAsRisk = false;

    private Long createdAt = System.currentTimeMillis();

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", picture='" + picture + '\'' +
                ", role='" + role + '\'' +
                ", kycStatus='" + kycStatus + '\'' +
                ", flaggedAsRisk=" + flaggedAsRisk +
                '}';
    }
}
