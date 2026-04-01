package com.pge.strategic.service.planning;

import com.pge.strategic.dto.planning.ActionPlanDTO;
import com.pge.strategic.model.planning.ActionPlan;
import com.pge.strategic.repository.planning.ActionPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ActionPlanService {

    private final ActionPlanRepository repository;

    @Transactional(readOnly = true)
    public List<ActionPlanDTO> findByObjectiveId(Long objectiveId) {
        return repository.findByObjectiveId(objectiveId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ActionPlanDTO findById(Long id) {
        return repository.findById(id)
                .map(this::toDTO)
                .orElse(null);
    }

    public ActionPlanDTO create(ActionPlanDTO dto) {
        ActionPlan entity = toEntity(dto);
        ActionPlan saved = repository.save(entity);
        return toDTO(saved);
    }

    public ActionPlanDTO update(Long id, ActionPlanDTO dto) {
        return repository.findById(id)
                .map(entity -> {
                    entity.setCode(dto.getCode());
                    entity.setTitle(dto.getTitle());
                    entity.setDescription(dto.getDescription());
                    entity.setStartDate(dto.getStartDate());
                    entity.setEndDate(dto.getEndDate());
                    entity.setBudget(dto.getBudget());
                    entity.setStatus(dto.getStatus());
                    ActionPlan updated = repository.save(entity);
                    return toDTO(updated);
                })
                .orElse(null);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    private ActionPlanDTO toDTO(ActionPlan entity) {
        ActionPlanDTO dto = new ActionPlanDTO();
        dto.setId(entity.getId());
        dto.setCode(entity.getCode());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        if (entity.getObjective() != null) {
            dto.setObjectiveId(entity.getObjective().getId());
        }
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        dto.setBudget(entity.getBudget());
        dto.setStatus(entity.getStatus().name());
        if (entity.getResponsibleUser() != null) {
            dto.setResponsibleUserId(entity.getResponsibleUser().getId().toString());
        }
        // Converter resources e tasks
        return dto;
    }

    private ActionPlan toEntity(ActionPlanDTO dto) {
        ActionPlan entity = new ActionPlan();
        entity.setCode(dto.getCode());
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
        entity.setBudget(dto.getBudget());
        // Objective, ResponsibleUser, Resources e Tasks precisam de tratamento adicional
        return entity;
    }
}
