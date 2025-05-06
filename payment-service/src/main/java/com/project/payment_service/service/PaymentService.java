package com.project.payment_service.service;

import com.project.payment_service.Dto.BankAccount;
import com.project.payment_service.Dto.DeductRequest;
import com.project.payment_service.client.BankAccountClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final BankAccountClient bankAccountClient;

    public void deductEmi(DeductRequest request) {

        BankAccount bankAccount = bankAccountClient.getBankAccountById(request.getBankAccountId());

        // 2. Ensure the account has sufficient balance for EMI deduction
        if (bankAccount.getBalance() >= request.getEmi()) {
            // 3. Deduct EMI from the user's bank account
            bankAccountClient.deductAmount(request.getBankAccountId(), request.getEmi());
            System.out.println("EMI Deducted: " + request.getEmi());
        } else {
            // Handle insufficient balance
            System.out.println("Insufficient funds in bank account for EMI deduction.");
        }
    }
}