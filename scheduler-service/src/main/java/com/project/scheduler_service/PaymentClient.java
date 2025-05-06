package com.project.scheduler_service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.project.scheduler_service.dto.DeductRequest;

@FeignClient(name = "payment-service", path = "/api/payments")
public interface PaymentClient {

    @PostMapping("/deduct")
    void deduct(@RequestBody DeductRequest request);

}
