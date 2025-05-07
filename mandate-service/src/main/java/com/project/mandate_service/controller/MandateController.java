package com.project.mandate_service.controller;
import com.project.mandate_service.dto.MandateRequestDto;
import com.project.mandate_service.entity.Mandate;
import com.project.mandate_service.service.MandateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/mandates")
@RequiredArgsConstructor
public class MandateController {

    private final MandateService mandateService;

    @PostMapping
    public Mandate createMandate(@RequestBody MandateRequestDto dto) {
        System.out.println(dto);
        return mandateService.createMandate(dto);
    }

    @GetMapping("/user/{userId}")
    public List<Mandate> getByUser(@PathVariable Long userId) {
        return mandateService.getMandatesByUserId(userId);
    }

    @GetMapping("/loan/{loanId}")
    public List<Mandate> getByLoan(@PathVariable Long loanId) {
        return mandateService.getMandatesByLoanId(loanId);
    }

    @PostMapping("/{id}/approve")
    public Mandate approve(@PathVariable Long id) {
        return mandateService.approveMandate(id);
    }

    @PostMapping("/{id}/reject")
    public Mandate reject(@PathVariable Long id) {
        return mandateService.rejectMandate(id);
    }


    @GetMapping("/approved/today")
    public List<Mandate> getMandatesDueToday() {
        return mandateService.getApprovedMandatesDueToday();
    }

    @GetMapping("/bank/{id}")
    public List<Mandate>findAllMandatesBankAccountId(@PathVariable Long id) {
        return mandateService.findAllMandatesByBankAccountId(id);
    }
}
