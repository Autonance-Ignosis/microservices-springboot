package com.project.mandate_service.clients;

import com.project.mandate_service.dto.BankAccountResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "bank-service", path = "/api/bank-accounts")
public interface BankAccountClient {

    @GetMapping("{id}")
    BankAccountResponse getBankAccount(@PathVariable("id") Long Id);
}