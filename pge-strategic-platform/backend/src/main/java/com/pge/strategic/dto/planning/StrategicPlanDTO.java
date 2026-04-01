package com.pge.strategic.dto.planning;

import lombok.Data;
import java.time.LocalDate;

@Data
public class StrategicPlanDTO {
    private Long id;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private Long institutionalIdentityId;
    private Boolean active;
}
