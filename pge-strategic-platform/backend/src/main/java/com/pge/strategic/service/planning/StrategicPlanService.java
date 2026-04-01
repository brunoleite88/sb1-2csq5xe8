package com.pge.strategic.service.planning;

import com.pge.strategic.dto.planning.StrategicPlanDTO;
import com.pge.strategic.model.planning.StrategicPlan;
import com.pge.strategic.repository.planning.StrategicPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class StrategicPlanService {

    private final StrategicPlanRepository repository;

    @Transactional(readOnly = true)
    public List<StrategicPlanDTO> findAll() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public StrategicPlanDTO findById(Long id) {
        return repository.findById(id)
                .map(this::toDTO)
                .orElse(null);
    }

    public StrategicPlanDTO create(StrategicPlanDTO dto) {
        StrategicPlan entity = toEntity(dto);
        StrategicPlan saved = repository.save(entity);
        return toDTO(saved);
    }

    public StrategicPlanDTO update(Long id, StrategicPlanDTO dto) {
        return repository.findById(id)
                .map(entity -> {
                    entity.setName(dto.getName());
                    entity.setDescription(dto.getDescription());
                    entity.setStartDate(dto.getStartDate());
                    entity.setEndDate(dto.getEndDate());
                    entity.setStatus(dto.getStatus());
                    entity.setActive(dto.getActive());
                    StrategicPlan updated = repository.save(entity);
                    return toDTO(updated);
                })
                .orElse(null);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    private StrategicPlanDTO toDTO(StrategicPlan entity) {
        StrategicPlanDTO dto = new StrategicPlanDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        dto.setStatus(entity.getStatus().name());
        dto.setActive(entity.getActive());
        if (entity.getInstitutionalIdentity() != null) {
            dto.setInstitutionalIdentityId(entity.getInstitutionalIdentity().getId());
        }
        return dto;
    }

    private StrategicPlan toEntity(StrategicPlanDTO dto) {
        StrategicPlan entity = new StrategicPlan();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
        entity.setActive(dto.getActive());
        // Status e InstitutionalIdentity precisam de tratamento adicional
        return entity;
    }
}
