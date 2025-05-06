package com.project.loan_service.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.loan_service.dto.LoanDto;
import com.project.loan_service.event.LoanEvent;
import com.project.loan_service.dto.LoanRequestDto;
import com.project.loan_service.dto.PredictionRequest;
import com.project.loan_service.entity.Loan;
import com.project.loan_service.repository.LoanRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final RestTemplate restTemplate;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public LoanService(LoanRepository loanRepository , KafkaTemplate<String, Object> kafkaTemplate, RestTemplate restTemplate) {
        this.loanRepository = loanRepository;
        this.restTemplate = restTemplate;
        this.kafkaTemplate = kafkaTemplate;

    }

    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    public List<LoanDto> getLoansDueToday() {
        LocalDate today = LocalDate.now();
        List<Loan> loans = loanRepository.findByNextEmiDate(today);

        return loans.stream()
                .map(loan -> new LoanDto(
                        loan.getId(),
                        loan.getUserId(),
                        loan.getBankAccountId(),
                        loan.getEmi(),
                        loan.getNextEmiDate()
                ))
                .collect(Collectors.toList());
    }

    public Loan applyLoan(LoanRequestDto dto) {
        double rate = dto.getInterestRate() / 100.0 / 12;
        int months = dto.getTenureInMonths();
        double emi = (dto.getAmount() * rate * Math.pow(1 + rate, months)) /
                (Math.pow(1 + rate, months) - 1);

        Map<String, Boolean> purposeMap = new HashMap<>();
        purposeMap.put("all_other", false);
        purposeMap.put("credit_card", false);
        purposeMap.put("debt_consolidation", false);
        purposeMap.put("educational", false);
        purposeMap.put("home_improvement", false);
        purposeMap.put("major_purchase", false);
        purposeMap.put("small_business", false);

        String userPurpose = dto.getPurpose();
        if (purposeMap.containsKey(userPurpose)) {
            purposeMap.put(userPurpose, true);
        }

        PredictionRequest requestPayload = PredictionRequest.builder()
                .interest_rate(dto.getInterestRate() / 100)
                .installment(emi)
                .log_of_income(dto.getLogIncome())
                .debt_income_ratio(dto.getDebtIncomeRatio())
                .fico_score(dto.getFicoScore())
                .days_with_credit_line(dto.getDaysWithCreditLine())
                .revolving_balance(dto.getRevolvingBalance())
                .revolving_utilization(dto.getRevolvingUtilization())
                .credit_criteria_meet(dto.getCreditCriteriaMet() ? 1 : 0)
                .inquiry_last_6months(dto.getInquiryLast6Months())
                .times_surpassed_payment_in_2yrs(dto.getTimesLateIn2Years())
                .derogatory_public_record(dto.getDerogatoryPublicRecords())

                // Purpose (one-hot)
                .purpose_all_other(purposeMap.get("all_other"))
                .purpose_credit_card(purposeMap.get("credit_card"))
                .purpose_debt_consolidation(purposeMap.get("debt_consolidation"))
                .purpose_educational(purposeMap.get("educational"))
                .purpose_home_improvement(purposeMap.get("home_improvement"))
                .purpose_major_purchase(purposeMap.get("major_purchase"))
                .purpose_small_business(purposeMap.get("small_business"))
                .build();

        Double riskProbability = null;

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<List<PredictionRequest>> request = new HttpEntity<>(List.of(requestPayload), headers);

            ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:5000/loan/predict", request,
                    String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                String body = response.getBody();
                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(body);
                riskProbability = root.get("prediction").get(0).get(0).asDouble(); // Get [[value]] → value
            }
        } catch (Exception e) {
            System.out.println("Error communicating with ML model: " + e.getMessage());
        }

        Loan loan = Loan.builder()
                .userId(dto.getUserId())
                .bankAccountId(dto.getBankAccountId())
                .loanOfferId(dto.getLoanOfferId())
                .appliedBankId(dto.getAppliedBankId())
                .appliedAt(LocalDate.now())
                .amount(dto.getAmount())
                .tenureInMonths(dto.getTenureInMonths())
                .interestRate(dto.getInterestRate())
                .emi(emi)
                .nextEmiDate(LocalDate.now().plusMonths(1))
                .status(Loan.LoanStatus.PENDING)

                // ML Input
                .creditCriteriaMet(dto.getCreditCriteriaMet())
                .purpose(dto.getPurpose())
                .logIncome(dto.getLogIncome())
                .debtIncomeRatio(dto.getDebtIncomeRatio())
                .ficoScore(dto.getFicoScore())
                .daysWithCreditLine(dto.getDaysWithCreditLine())
                .revolvingBalance(dto.getRevolvingBalance())
                .revolvingUtilization(dto.getRevolvingUtilization())
                .inquiryLast6Months(dto.getInquiryLast6Months())
                .timesLateIn2Years(dto.getTimesLateIn2Years())
                .derogatoryPublicRecords(dto.getDerogatoryPublicRecords())

                // ML Output
                .defaultRiskProbability(riskProbability)
                .build();

        return loanRepository.save(loan);
    }

    public Loan getLoanById(Long id) {
        return loanRepository.findById(id).orElseThrow();
    }

    public List<Loan> getLoansByBankId(Long bankId) {
        return loanRepository.findByAppliedBankId(bankId);
    }

    public List<Loan> getLoansByUserId(Long userId) {
        return loanRepository.findByUserId(userId);
    }

    public Loan approveLoan(Long loanId) {
        Loan loan = getLoanById(loanId);
        loan.setStatus(Loan.LoanStatus.APPROVED);
        loanRepository.save(loan);

        LoanEvent event = new LoanEvent(loan.getUserId(), "APPROVED", loan.getAmount());
        kafkaTemplate.send("loan-events", event);

        return loan;
    }

    public Loan rejectLoan(Long loanId) {
        Loan loan = getLoanById(loanId);
        loan.setStatus(Loan.LoanStatus.REJECTED);
        return loanRepository.save(loan);
    }
}