package com.project.scheduler_service.config;

import com.project.scheduler_service.PaymentClient;
import com.project.scheduler_service.client.LoanServiceClient;
import com.project.scheduler_service.client.MandateServiceClient;
import com.project.scheduler_service.dto.DeductRequest;
import com.project.scheduler_service.dto.LoanDto;
import com.project.scheduler_service.dto.MandateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmiScheduler {

    private final MandateServiceClient mandateClient;
    private final LoanServiceClient loanClient;
    private final PaymentClient paymentClient;

    @Scheduled(cron = "0 0 8 * * *") // Every day at 8 AM
    public void processEmiDeductions() {
        List<MandateDto> mandates = mandateClient.getTodayApprovedMandates();

        for (MandateDto mandate : mandates) {
            LoanDto loan = loanClient.getLoanById(mandate.getLoanId());

            DeductRequest request = new DeductRequest(
                    mandate.getUserId(),
                    mandate.getBankAccountId(),
                    loan.getEmi(),
                    mandate.getLoanId()
            );

            paymentClient.deduct(request);
        }
    }
}