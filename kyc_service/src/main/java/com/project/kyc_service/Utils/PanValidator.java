package com.project.kyc_service.Utils;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Component
public class PanValidator {

    private static final Logger logger = Logger.getLogger(PanValidator.class.getName());
    private static final String KARZA_API_URL = "https://api.karza.in/v3/pan-link";

    private final RestTemplate restTemplate;

    public PanValidator() {
        this.restTemplate = new RestTemplate();
    }

    public Map<String, Object> validatePan(String pan) {
        try {
            String apiKey = System.getenv("KARZA_API_KEY");

            if (apiKey == null || apiKey.isEmpty()) {
                throw new IllegalStateException("KARZA_API_KEY is not set in environment variables");
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(java.util.Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.set("x-karza-key", apiKey);

            Map<String, Object> payload = new HashMap<>();
            payload.put("pan", pan);
            payload.put("consent", "Y");

            Map<String, Object> clientData = new HashMap<>();
            clientData.put("caseId", "123456");
            payload.put("clientData", clientData);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

            logger.info("Sending PAN validation request...");

            ResponseEntity<Map> response = restTemplate.exchange(
                    KARZA_API_URL,
                    HttpMethod.POST,
                    request,
                    Map.class
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                Map<String, Object> resultJson = response.getBody();
                logger.info("Response: " + resultJson);

                Map<String, Object> resultMap = new HashMap<>();
                resultMap.put("statusCode", resultJson.get("statusCode"));

                Map<String, Object> resultInner = (Map<String, Object>) resultJson.get("result");
                boolean isAadhaarLinked = resultInner != null && Boolean.TRUE.equals(resultInner.get("isAadhaarLinked"));

                resultMap.put("isAadhaarLinked", isAadhaarLinked);
                logger.info("Updated PAN [" + pan + "] with result: " + resultMap);

                return resultMap;
            } else {
                Map<String, Object> error = new HashMap<>();
                error.put("error", "HTTP Error " + response.getStatusCode());
                error.put("details", response.getBody());
                return error;
            }

        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Exception");
            error.put("message", e.getMessage());
            return error;
        }
    }
}
