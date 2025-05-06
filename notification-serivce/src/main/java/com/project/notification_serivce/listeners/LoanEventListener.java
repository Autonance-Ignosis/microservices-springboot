package com.project.notification_serivce.listeners;

import com.project.loan_service.event.LoanEvent;
import com.project.notification_serivce.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoanEventListener {

    private final NotificationService notificationService;

    @KafkaListener(topics = "loan-events", groupId = "notification-group")
    public void handleLoanEvent(@Payload LoanEvent event) {
        notificationService.notifyLoanStatus(event);
    }
}