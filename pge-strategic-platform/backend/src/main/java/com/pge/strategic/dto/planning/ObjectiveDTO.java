package com.pge.strategic.dto.planning;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ObjectiveDTO {
    private Long id;
    private String code;
    private String description;
    private Long bscPerspectiveId;
    private Long strategicAxisId;
    private String responsibleUnitId;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal targetValue;
    private String measurementUnit;
    private String status;
}
