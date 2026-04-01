package com.pge.strategic.dto.planning;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class KpiMeasurementDTO {
    private Long id;
    private Long kpiId;
    private BigDecimal measuredValue;
    private LocalDate measurementDate;
    private String observation;
    private String evidenceUrl;
    private String registeredByUserId;
}
