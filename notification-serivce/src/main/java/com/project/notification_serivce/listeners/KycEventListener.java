package com.project.notification_serivce.listeners;

import com.project.kyc_service.event.KycEvent;
import com.project.notification_serivce.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KycEventListener {
     private final NotificationService notificationService;

     @KafkaListener(topics = "kyc-events", groupId = "notification-group")
     public void handleKycEvent(@Payload KycEvent event) {
         notificationService.notifyKycStatus(event);
     }
}
