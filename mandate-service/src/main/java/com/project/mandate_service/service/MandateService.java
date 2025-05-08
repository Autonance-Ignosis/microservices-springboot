package com.project.mandate_service.service;


import com.project.mandate_service.clients.BankAccountClient;
import com.project.mandate_service.clients.LoanClient;
import com.project.mandate_service.dto.BankAccountResponse;
import com.project.mandate_service.dto.LoanDto;
import com.project.mandate_service.dto.MandateRequestDto;
import com.project.mandate_service.dto.PredictionResult;
import com.project.mandate_service.entity.Mandate;
import com.project.mandate_service.event.MandateEvent;
import com.project.mandate_service.repository.MandateRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class  MandateService {

    private final MandateRepository mandateRepository;
    private final LoanClient loanClient;
    private final BankAccountClient bankAccountClient;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private RestTemplate restTemplate;  // Autowire RestTemplate

    private static final String FLASK_API_URL = "http://127.0.0.1:5000/mandate/predict";  // Flask server URL

    public Mandate getMandateById(Long id) {
        return mandateRepository.findById(id).orElseThrow();
    }

    public Mandate createMandate(MandateRequestDto dto) {
        // Check for existing mandate
        if (mandateRepository.findByLoanIdAndUserId(dto.getLoanId(), dto.getUserId()).isPresent()) {
            throw new RuntimeException("Mandate already exists for this user and loan.");
        }

        // Validate Loan
        if (!loanIsValid(dto.getLoanId(), dto.getUserId())) {
            throw new RuntimeException("Invalid or unauthorized loan.");
        }

        // All checks passed, create mandate
        Mandate mandate = new Mandate();
        mandate.setLoanId(dto.getLoanId());
        mandate.setUserId(dto.getUserId());
        mandate.setBankAccountId(dto.getBankAccountId());

        // Set Mandate Details
        mandate.setMandateVariant(dto.getMandateVariant());
        mandate.setCategory(dto.getCategory());
        mandate.setDebitType(dto.getDebitType());
        mandate.setSeqType(dto.getSeqType());
        mandate.setFreqType(dto.getFreqType());
        mandate.setSchemaName(dto.getSchemaName());
        mandate.setConsRefNo(dto.getConsRefNo());
        mandate.setAmount(dto.getAmount());
        mandate.setStartDate(dto.getStartDate());
        mandate.setUptoDate(dto.getUptoDate());
        mandate.setUpTo40Years(dto.getUpTo40Years());

        // Call Flask API to predict the mandate status
//        PredictionResult predictionResult = predictMandateStatus(dto);
//        mandate.setPredictionStatus(predictionResult.getStatus());
//        mandate.setPredictionProbability(predictionResult.getProbability());
        mandate.setUpdatedAt(System.currentTimeMillis());

        return mandateRepository.save(mandate);
    }


    private PredictionResult predictMandateStatus(MandateRequestDto dto) {
        Map<String, Object> requestBody = new LinkedHashMap<>();
        requestBody.put("amount", dto.getAmount());
        requestBody.put("category", dto.getCategory().toString());
        requestBody.put("mandate_variant", dto.getMandateVariant().toString());
        requestBody.put("debit_type", dto.getDebitType().toString());
        requestBody.put("seq_type", dto.getSeqType().toString());
        requestBody.put("freq_type", dto.getFreqType().toString());
        requestBody.put("up_to_40_years", dto.getUpTo40Years());
        requestBody.put("start_date", dto.getStartDate());
        requestBody.put("upto_date", dto.getUptoDate());

        ResponseEntity<Map> response = restTemplate.postForEntity(FLASK_API_URL, requestBody, Map.class);
        Map<String, Object> predictionResult = response.getBody();

        if (predictionResult != null) {
            String status = predictionResult.get("status").toString(); // "APPROVED" or "REJECTED"

            Map<String, Object> probabilityMap = (Map<String, Object>) predictionResult.get("probability");
            Object probValue = probabilityMap.get(status); // e.g., probability.get("REJECTED")

            Double probability = null;
            if (probValue instanceof Number) {
                probability = ((Number) probValue).doubleValue();
            }

            return new PredictionResult(status, probability);
        } else {
            throw new RuntimeException("Error in Flask API response.");
        }
    }


    public List<Mandate> findAllMandatesByBankAccountId(Long BankAccountId) {
        return mandateRepository.findAllMandatesByBankAccountId(BankAccountId);
    }

    private boolean loanIsValid(Long loanId, Long userId) {
        System.out.println(loanId);
        System.out.println(userId);
        try {
            LoanDto loan = loanClient.getLoan(loanId);
            System.out.println(loan);
            System.out.println(loan.getUserId());
            return loan != null && loan.getUserId().equals(userId) && "APPROVED".equals(loan.getStatus());
        } catch (Exception e) {
            return false;
        }
    }

    private boolean bankAccountIsValid(Long bankAccountId, Long userId) {
        try {
            System.out.println("BankAccountId: " + bankAccountId);
            BankAccountResponse account = bankAccountClient.getBankAccount(bankAccountId);
            System.out.println("Account: " + account);

            return account != null && account.getUserId().equals(userId);
        } catch (Exception e) {
            return false;
        }
    }

    public List<Mandate> getMandatesByUserId(Long userId) {
        return mandateRepository.findByUserId(userId);
    }

    public List<Mandate> getMandatesByLoanId(Long loanId) {
        return mandateRepository.findByLoanId(loanId);
    }

    public Mandate approveMandate(Long id) {
        Mandate mandate = mandateRepository.findById(id).orElseThrow();
        mandate.setStatus("APPROVED");
        mandate.setUpdatedAt(System.currentTimeMillis());
        mandateRepository.save(mandate);

        kafkaTemplate.send("mandate-events", new MandateEvent(mandate.getUserId(), "APPROVED"));
        return mandate;
    }

    public Mandate rejectMandate(Long id) {
        Mandate mandate = mandateRepository.findById(id).orElseThrow();
        mandate.setStatus("REJECTED");
        mandate.setUpdatedAt(System.currentTimeMillis());
        mandateRepository.save(mandate);

        kafkaTemplate.send("mandate-events", new MandateEvent(mandate.getUserId(), "REJECTED"));
        return mandate;
    }

    public List<Mandate> getApprovedMandatesDueToday() {
        List<Mandate> approvedMandates = mandateRepository.findApprovedMandates();
        LocalDate today = LocalDate.now();

        return approvedMandates.stream()
                .filter(mandate -> {
                    // Call loan service to get loan details
                    LoanDto loan = loanClient.getLoan(mandate.getLoanId());
                    return loan.getNextEmiDate().isEqual(today);
                })
                .toList();
    }


    public List<Mandate> getMandatesByLoanOfBankId(Long bankId) {
        List<LoanDto> loans = loanClient.getLoansByBank(bankId);


        List<Long> loanIds = loans.stream()
                .filter(loan -> loan.getAppliedBankId().equals(bankId))
                .map(LoanDto::getId)
                .toList();

        return mandateRepository.findByLoanIdIn(loanIds);


    }
}
