package com.project.mandate_service.event;

import lombok.Data;

@Data
public class MandateEvent {
    private Long userId;
    private String status;
    private Long mandateId;
}
