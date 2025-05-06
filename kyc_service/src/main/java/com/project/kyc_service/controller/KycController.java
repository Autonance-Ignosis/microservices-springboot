package com.project.kyc_service.controller;
import com.project.kyc_service.Utils.PanValidator;
import com.project.kyc_service.Utils.adharValidator;
import com.project.kyc_service.entity.KycRecord;
import com.project.kyc_service.services.CloudinaryService;
import com.project.kyc_service.services.KycService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = {"http://localhost:5174","http://localhost:5173"})
@RestController
@RequestMapping("/api/kyc")
@RequiredArgsConstructor
public class KycController {

    private final KycService kycService;
    private final CloudinaryService cloudinaryService ;
    private final PanValidator panValidator;
    private final adharValidator adharValidator;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadKyc(
            @RequestParam("userId") int userId,
            @RequestParam("panNo") String panNo,
            @RequestParam("aadhaarNo") String aadhaarNo,
            @RequestParam("panFile") MultipartFile panFile,
            @RequestParam("aadhaarFile") MultipartFile aadhaarFile
    ) throws Exception {

        List<KycRecord> records = kycService.getKycRecordsByUserId(userId);

        if (!records.isEmpty()) {
            KycRecord latest = records.get(0);
            if (latest.getStatus().equalsIgnoreCase("PENDING")) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("KYC already submitted and is under review.");
            }

            if(latest.getStatus().equalsIgnoreCase("VERIFIED")) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("KYC already verified.");
            }
        }

        String panFileUrl = cloudinaryService.uploadFile(panFile, "kyc");
        String aadhaarFileUrl = cloudinaryService.uploadFile(aadhaarFile, "kyc");

        KycRecord saved = kycService.uploadKyc(
                userId, panNo, aadhaarNo, panFileUrl, aadhaarFileUrl
        );

        return ResponseEntity.ok(saved);
    }


    // Get KYC status
    @GetMapping("/status/{userId}")
    public ResponseEntity<KycRecord> getStatus(@PathVariable int userId) {

        List<KycRecord> records = kycService.getKycRecordsByUserId(userId);

        if (records.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        KycRecord latest = records.get(0);

        if (latest.getStatus().equalsIgnoreCase("PENDING")) {
            return ResponseEntity.status(HttpStatus.OK).body(latest);
        }

        // If the latest record is not pending, return the latest record
        return ResponseEntity.ok(latest);
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("KYC Service is running");
    }

    // Admin/Auto-verifier: Update KYC status
    @PutMapping("/update/{userId}")
    public ResponseEntity<?> updateStatus(
            @PathVariable int userId,
            @RequestParam String status,
            @RequestParam String remarks
    ) {
        KycRecord updated = kycService.updateStatus(userId, status, remarks);
        return ResponseEntity.ok(updated);
    }


//    @PostMapping("/validate-pan")
//    public ResponseEntity<?> validatePan(@RequestBody Map<String, String> requestBody) {
//        String pan = requestBody.get("pan");
//        if (pan == null || pan.isEmpty()) {
//            return ResponseEntity.badRequest().body(Map.of("error", "PAN is required"));
//        }
//
//        Map<String, Object> result = panValidator.validatePan(pan);
//        return ResponseEntity.ok(result);
//    }

    @GetMapping("/status/all")
    public List<KycRecord>  getAllStatus() {return kycService.getKycStatus();}

    @PostMapping("/pan-adhar")
    public ResponseEntity<?> verifyPanAndAadhaar(@RequestBody Map<String, String> requestBody) {
        String panUrl = requestBody.get("pan_url");
        String aadhaarUrl = requestBody.get("aadhaar_url");
        String panno = requestBody.get("pan_no");
        String aadhaarno = requestBody.get("aadhar_no");
        String pn,an;

        if (panUrl == null || panUrl.isEmpty() || aadhaarUrl == null || aadhaarUrl.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "PAN and Aadhaar URLs are required"));
        }

        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, String> payload = Map.of("url", panUrl);
            HttpEntity<Map<String, String>> request = new HttpEntity<>(payload, headers);

            Map<String, String> pl2 = Map.of("url", aadhaarUrl);
            HttpEntity<Map<String, String>> rq2 = new HttpEntity<>(pl2, headers);

            // Make the POST request to Flask server
            ResponseEntity<Map> response = restTemplate.postForEntity(
                    "http://localhost:5000/kyc/pan", request, Map.class
            );

            ResponseEntity<Map> r2 = restTemplate.postForEntity(
                    "http://localhost:5000/kyc/aadhaar", rq2, Map.class
            );

            Map<String, Object> responseBody = response.getBody();
            Map<String, Object> r2b = r2.getBody();

            if (responseBody != null) {
                System.out.println("Response from Flask: " + responseBody);
            }

            pn = responseBody.get("pan_number").toString();
            an = r2b.get("aadhaar_number").toString();


        } catch (Exception e) {
            System.out.println("Error calling Flask service: " + e.getMessage());

            return ResponseEntity.status(201)
                    .body(Map.of("error", "Provide Better Resolution Image or Error Details: " + e.getMessage()));
        }

        System.out.println("PAN: " + pn + " Aadhaar: " + an);

//        if (!(panno.equalsIgnoreCase(pn) && aadhaarno.equalsIgnoreCase(an)) ){
//            return ResponseEntity.status(201)
//                    .body(Map.of("error", "Provide Better Resolution Image or Error Details: " ));
//        }

        Map<String, Object> karzaStatus = panValidator.validatePan(pn);
        System.out.println("Karza Status: " + karzaStatus);

//        Map<String, Object> aadhaarStatus = adharValidator.validateAadhaar(an);
//        System.out.println("Aadhaar Status: " + aadhaarStatus);




        if (karzaStatus.get("statusCode").equals(101) ) {
            return ResponseEntity.status(200)
                    .body(Map.of("msg","Adhar is linked to PAN and verified"));
        }

        return ResponseEntity.status(201).body(Map.of("error", "Provide Better Resolution Image or Error Details: " ));

    }
}
