package com.pge.strategic.dto.planning;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ActionTaskDTO {
    private Long id;
    private String description;
    private LocalDate dueDate;
    private String status;
    private String responsibleUserId;
    private Integer completionPercentage;
}
