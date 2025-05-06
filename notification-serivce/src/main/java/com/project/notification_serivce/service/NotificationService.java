package com.project.notification_serivce.service;

import com.project.loan_service.event.LoanEvent;
import com.project.mandate_service.event.MandateEvent;
import com.project.notification_serivce.config.NotificationSocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class NotificationService {

    private final NotificationSocketHandler socketHandler;

    public void sendNotification(Long userId, String message) {
        try {
            socketHandler.sendNotification(userId, message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void notifyLoanStatus(LoanEvent event) {
        String msg = "Loan of ₹" + event.getAmount() + " is " + event.getStatus();
        System.out.println("Sending notification: " + msg);
        sendNotification(event.getUserId(), msg);
    }

    public void notifyMandateStatus(MandateEvent event) {
        String msg = "Mandate is " + event.getStatus();
        System.out.println("Sending notification: " + msg);
        sendNotification(event.getUserId(), msg);
    }


}
