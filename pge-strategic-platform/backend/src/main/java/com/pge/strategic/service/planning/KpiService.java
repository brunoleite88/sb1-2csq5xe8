package com.pge.strategic.service.planning;

import com.pge.strategic.dto.planning.KpiDTO;
import com.pge.strategic.model.planning.Kpi;
import com.pge.strategic.repository.planning.KpiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class KpiService {

    private final KpiRepository repository;

    @Transactional(readOnly = true)
    public List<KpiDTO> findByObjectiveId(Long objectiveId) {
        return repository.findByObjectiveId(objectiveId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public KpiDTO findById(Long id) {
        return repository.findById(id)
                .map(this::toDTO)
                .orElse(null);
    }

    public KpiDTO create(KpiDTO dto) {
        Kpi entity = toEntity(dto);
        Kpi saved = repository.save(entity);
        return toDTO(saved);
    }

    public KpiDTO update(Long id, KpiDTO dto) {
        return repository.findById(id)
                .map(entity -> {
                    entity.setCode(dto.getCode());
                    entity.setName(dto.getName());
                    entity.setDescription(dto.getDescription());
                    entity.setFormula(dto.getFormula());
                    entity.setMeasurementUnit(dto.getMeasurementUnit());
                    entity.setTargetValue(dto.getTargetValue());
                    entity.setMinValue(dto.getMinValue());
                    entity.setMaxValue(dto.getMaxValue());
                    entity.setFrequencyDays(dto.getFrequencyDays());
                    Kpi updated = repository.save(entity);
                    return toDTO(updated);
                })
                .orElse(null);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    private KpiDTO toDTO(Kpi entity) {
        KpiDTO dto = new KpiDTO();
        dto.setId(entity.getId());
        dto.setCode(entity.getCode());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        if (entity.getObjective() != null) {
            dto.setObjectiveId(entity.getObjective().getId());
        }
        dto.setFormula(entity.getFormula());
        dto.setMeasurementUnit(entity.getMeasurementUnit());
        dto.setTargetValue(entity.getTargetValue());
        dto.setMinValue(entity.getMinValue());
        dto.setMaxValue(entity.getMaxValue());
        dto.setFrequencyDays(entity.getFrequencyDays());
        dto.setNextMeasurementDate(entity.getNextMeasurementDate());
        if (entity.getResponsibleUser() != null) {
            dto.setResponsibleUserId(entity.getResponsibleUser().getId().toString());
        }
        return dto;
    }

    private Kpi toEntity(KpiDTO dto) {
        Kpi entity = new Kpi();
        entity.setCode(dto.getCode());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setFormula(dto.getFormula());
        entity.setMeasurementUnit(dto.getMeasurementUnit());
        entity.setTargetValue(dto.getTargetValue());
        entity.setMinValue(dto.getMinValue());
        entity.setMaxValue(dto.getMaxValue());
        entity.setFrequencyDays(dto.getFrequencyDays());
        // Objective e ResponsibleUser precisam de tratamento adicional
        return entity;
    }
}
