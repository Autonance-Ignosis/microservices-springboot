package com.project.payment_service.client;

import com.project.payment_service.Dto.BankAccount;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "bank-service", path = "/api/bank-accounts")
public interface BankAccountClient {

    @GetMapping("/{id}")
    BankAccount getBankAccountById(@PathVariable("id") Long id);

    @PostMapping("/{id}/deduct")
    void deductAmount(@PathVariable("id") Long id, @RequestParam("amount") Double amount);
}