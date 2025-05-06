package com.project.scheduler_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.project.scheduler_service.dto.LoanDto;

import java.util.List;

@FeignClient(name = "loan-service" , path = "/api/loans")
public interface LoanServiceClient {
    @GetMapping("/due-today")
    List<LoanDto> getLoansDueToday();

    @PostMapping("/{id}/next-emi-date")
    void updateNextEmiDate(@PathVariable("id") Long loanId);


    @GetMapping("/{id}")
    LoanDto getLoanById(@PathVariable("id") Long loanId);
}
