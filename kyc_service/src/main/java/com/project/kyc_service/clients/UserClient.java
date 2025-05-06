package com.project.kyc_service.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "USER-SERVICE", path = "/api/user")
public interface UserClient {

    @PutMapping("/updateKycStatus/{userId}")
    void updateKycStatus(@PathVariable("userId") int userId, @RequestParam("status") String status);
}