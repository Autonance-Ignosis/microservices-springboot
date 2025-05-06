package com.project.notification_serivce.service;

import com.project.loan_service.event.LoanEvent;
import com.project.mandate_service.event.MandateEvent;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public void notifyLoanStatus(LoanEvent event) {
        String msg = "Your loan of ₹" + event.getAmount() + " has been " + event.getStatus();
        send(event.getUserId(), msg);
    }

    public void notifyMandateStatus(MandateEvent event) {
        String msg = "Your mandate has been " + event.getStatus();
        send(event.getUserId(), msg);
    }

//    public void notifyPaymentStatus(PaymentEvent event) {
//        String msg = event.isSuccess()
//                ? "EMI of ₹" + event.getAmount() + " has been deducted successfully."
//                : "EMI deduction of ₹" + event.getAmount() + " failed due to insufficient balance.";
//        send(event.getUserId(), msg);
//    }

    private void send(Long userId, String message) {
        // For now, just print to console
        System.out.println("Notification to User[" + userId + "]: " + message);
    }
}
