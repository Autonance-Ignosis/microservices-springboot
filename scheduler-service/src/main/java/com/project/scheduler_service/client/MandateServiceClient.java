package com.project.scheduler_service.client;
import com.project.scheduler_service.dto.MandateDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@FeignClient(name = "mandate-service" , path = "/api/mandates" )
public interface MandateServiceClient {
    @GetMapping("/loan/{loanId}")
    MandateDto getMandateByLoanId(@PathVariable("loanId") Long loanId);


    @GetMapping("/mandates/approved/today")
    List<MandateDto> getTodayApprovedMandates();
}
