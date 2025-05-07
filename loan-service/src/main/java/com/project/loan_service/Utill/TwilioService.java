package com.project.loan_service.Utill;

import com.twilio.Twilio;
import com.twilio.exception.ApiException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TwilioService {

    @Value("${twilio.account-sid}")
    private String accountSid;

    @Value("${twilio.auth-token}")
    private String authToken;

    @Value("${twilio.phone-number}")
    private String phoneFrom;

    public void sendSms(String phoneTo, String messageBody) {
        try {
            Twilio.init(accountSid, authToken);
//            Message message = Message.creator(
//                    new PhoneNumber(phoneTo),
//                    new PhoneNumber(phoneFrom),
//                    messageBody
//            ).create();
//            System.out.println("SMS Sent: " + message.getSid());
            Message message = Message
                    .creator(new com.twilio.type.PhoneNumber(phoneTo),
                            new com.twilio.type.PhoneNumber(phoneFrom),
                            messageBody)
                    .create();

            System.out.println(message.getBody());
        } catch (ApiException e) {
            System.err.println("Error sending SMS: " + e.getMessage());
        }
    }
}
