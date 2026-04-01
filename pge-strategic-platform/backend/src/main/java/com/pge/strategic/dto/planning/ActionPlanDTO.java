package com.pge.strategic.dto.planning;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class ActionPlanDTO {
    private Long id;
    private String code;
    private String title;
    private String description;
    private Long objectiveId;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal budget;
    private String status;
    private String responsibleUserId;
    private List<Long> resourceIds;
    private List<ActionTaskDTO> tasks;
}
