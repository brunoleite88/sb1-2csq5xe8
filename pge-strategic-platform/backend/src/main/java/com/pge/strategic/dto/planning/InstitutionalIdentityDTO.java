package com.pge.strategic.dto.planning;

import lombok.Data;
import java.util.List;

@Data
public class InstitutionalIdentityDTO {
    private Long id;
    private String mission;
    private String vision;
    private List<String> values;
    private String slogan;
    private String logoUrl;
}
