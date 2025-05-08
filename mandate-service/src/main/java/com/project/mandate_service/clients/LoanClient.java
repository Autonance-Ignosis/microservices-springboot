package com.project.mandate_service.clients;

import com.project.mandate_service.dto.LoanDto;
import com.project.mandate_service.dto.LoanResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.List;


@FeignClient(name = "loan-service", path = "/api/loans")
public interface LoanClient {

    @GetMapping("{loanId}")
    LoanDto getLoan(@PathVariable("loanId") Long loanId);

    @GetMapping("/bank/{bankId}")
    List<LoanDto> getLoansByBank(@PathVariable Long bankId);

}
