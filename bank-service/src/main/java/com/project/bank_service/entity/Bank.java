package com.project.bank_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "banks")
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // Name of the bank
    private String ifsc; // Unique IFSC code for the bank


    @Column(name = "admin_user_id")
    private Long adminUserId; // Bank admin (userId from User service)

    @Override
    public String toString() {
        return "Bank{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ifsc='" + ifsc + '\'' +
                ", adminUserId=" + adminUserId +
                '}';
    }


}