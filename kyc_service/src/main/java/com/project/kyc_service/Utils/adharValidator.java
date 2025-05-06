package com.project.kyc_service.Utils;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class adharValidator {

    private static final String API_URL = "https://api.apyhub.com/validate/aadhaar";
    private final RestTemplate restTemplate;

    public adharValidator() {
        this.restTemplate = new RestTemplate();
    }

    public Map<String, Object> validateAadhaar(String aadhaarNumber) {
        String token = System.getenv("APYHUB_AADHAAR_API_TOKEN");

        if (token == null || token.isEmpty()) {
            throw new IllegalStateException("APYHUB_AADHAAR_API_TOKEN not set in environment variables");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("apy-token", token);

        Map<String, String> payload = new HashMap<>();
        payload.put("aadhaar", aadhaarNumber);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(payload, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(API_URL, request, Map.class);
            return response.getBody();
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Failed to call ApyHub Aadhaar API");
            error.put("message", e.getMessage());
            return error;
        }
    }
}
