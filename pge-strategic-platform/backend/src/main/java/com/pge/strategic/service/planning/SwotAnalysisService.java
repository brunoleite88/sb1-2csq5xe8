package com.pge.strategic.service.planning;

import com.pge.strategic.dto.planning.SwotAnalysisDTO;
import com.pge.strategic.model.planning.SwotAnalysis;
import com.pge.strategic.repository.planning.SwotAnalysisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SwotAnalysisService {

    private final SwotAnalysisRepository repository;

    @Transactional(readOnly = true)
    public List<SwotAnalysisDTO> findByPlanId(Long planId) {
        return repository.findByPlanId(planId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SwotAnalysisDTO findById(Long id) {
        return repository.findById(id)
                .map(this::toDTO)
                .orElse(null);
    }

    public SwotAnalysisDTO create(SwotAnalysisDTO dto) {
        SwotAnalysis entity = toEntity(dto);
        SwotAnalysis saved = repository.save(entity);
        return toDTO(saved);
    }

    public SwotAnalysisDTO update(Long id, SwotAnalysisDTO dto) {
        return repository.findById(id)
                .map(entity -> {
                    entity.setTitle(dto.getTitle());
                    entity.setDescription(dto.getDescription());
                    SwotAnalysis updated = repository.save(entity);
                    return toDTO(updated);
                })
                .orElse(null);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    private SwotAnalysisDTO toDTO(SwotAnalysis entity) {
        SwotAnalysisDTO dto = new SwotAnalysisDTO();
        dto.setId(entity.getId());
        dto.setPlanId(entity.getPlan().getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        // Converter listas de itens (strengths, weaknesses, opportunities, threats)
        return dto;
    }

    private SwotAnalysis toEntity(SwotAnalysisDTO dto) {
        SwotAnalysis entity = new SwotAnalysis();
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        // Plan e listas de itens precisam de tratamento adicional
        return entity;
    }
}
