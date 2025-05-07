package com.project.kyc_service.event;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KycEvent {
    private Long userId;
    private String status;  // "APPROVED" or "REJECTED"
    private String message;
}
