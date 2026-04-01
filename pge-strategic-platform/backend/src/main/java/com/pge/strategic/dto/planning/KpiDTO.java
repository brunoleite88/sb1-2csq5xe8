package com.pge.strategic.dto.planning;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class KpiDTO {
    private Long id;
    private String code;
    private String name;
    private String description;
    private Long objectiveId;
    private String formula;
    private String measurementUnit;
    private BigDecimal targetValue;
    private BigDecimal minValue;
    private BigDecimal maxValue;
    private Integer frequencyDays;
    private LocalDate nextMeasurementDate;
    private String responsibleUserId;
}
