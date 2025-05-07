package com.project.mandate_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PredictionResult {
    private String status;
    private Double probability;
}
