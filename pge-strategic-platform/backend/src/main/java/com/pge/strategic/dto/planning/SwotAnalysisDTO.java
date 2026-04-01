package com.pge.strategic.dto.planning;

import lombok.Data;
import java.util.List;

@Data
public class SwotAnalysisDTO {
    private Long id;
    private Long planId;
    private String title;
    private String description;
    private List<StringItemDTO> strengths;
    private List<StringItemDTO> weaknesses;
    private List<StringItemDTO> opportunities;
    private List<StringItemDTO> threats;
}
