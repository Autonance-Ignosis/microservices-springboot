package com.project.notification_serivce.listeners;


import com.project.mandate_service.event.MandateEvent;
import com.project.notification_serivce.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MandateEventListener {

    private final NotificationService notificationService;

    @KafkaListener(topics = "mandate-events", groupId = "notification-group")
    public void handleMandateEvent(@Payload MandateEvent event) {
        notificationService.notifyMandateStatus(event);
    }
}
