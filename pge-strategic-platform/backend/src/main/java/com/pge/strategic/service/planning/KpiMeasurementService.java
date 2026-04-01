package com.pge.strategic.service.planning;

import com.pge.strategic.dto.planning.KpiMeasurementDTO;
import com.pge.strategic.model.planning.Kpi;
import com.pge.strategic.model.planning.KpiMeasurement;
import com.pge.strategic.repository.planning.KpiMeasurementRepository;
import com.pge.strategic.repository.planning.KpiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class KpiMeasurementService {

    private final KpiMeasurementRepository repository;
    private final KpiRepository kpiRepository;

    @Transactional(readOnly = true)
    public List<KpiMeasurementDTO> findByKpiId(Long kpiId) {
        return repository.findByKpiIdOrderByMeasurementDateDesc(kpiId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public KpiMeasurementDTO findById(Long id) {
        return repository.findById(id)
                .map(this::toDTO)
                .orElse(null);
    }

    public KpiMeasurementDTO create(KpiMeasurementDTO dto) {
        KpiMeasurement entity = toEntity(dto);
        
        // Atualizar próxima data de medição do KPI
        Kpi kpi = kpiRepository.findById(dto.getKpiId()).orElse(null);
        if (kpi != null) {
            kpi.setNextMeasurementDate(dto.getMeasurementDate().plusDays(kpi.getFrequencyDays()));
            kpiRepository.save(kpi);
            
            // Verificar alertas (meta atingida, abaixo do mínimo, acima do máximo)
            if (dto.getMeasuredValue().compareTo(kpi.getTargetValue()) >= 0) {
                // Meta atingida
            } else if (dto.getMeasuredValue().compareTo(kpi.getMinValue()) < 0) {
                // Abaixo do mínimo - gerar alerta
            } else if (dto.getMeasuredValue().compareTo(kpi.getMaxValue()) > 0) {
                // Acima do máximo - gerar alerta
            }
        }
        
        KpiMeasurement saved = repository.save(entity);
        return toDTO(saved);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    private KpiMeasurementDTO toDTO(KpiMeasurement entity) {
        KpiMeasurementDTO dto = new KpiMeasurementDTO();
        dto.setId(entity.getId());
        if (entity.getKpi() != null) {
            dto.setKpiId(entity.getKpi().getId());
        }
        dto.setMeasuredValue(entity.getMeasuredValue());
        dto.setMeasurementDate(entity.getMeasurementDate());
        dto.setObservation(entity.getObservation());
        dto.setEvidenceUrl(entity.getEvidenceUrl());
        if (entity.getRegisteredByUser() != null) {
            dto.setRegisteredByUserId(entity.getRegisteredByUser().getId().toString());
        }
        return dto;
    }

    private KpiMeasurement toEntity(KpiMeasurementDTO dto) {
        KpiMeasurement entity = new KpiMeasurement();
        entity.setMeasuredValue(dto.getMeasuredValue());
        entity.setMeasurementDate(dto.getMeasurementDate());
        entity.setObservation(dto.getObservation());
        entity.setEvidenceUrl(dto.getEvidenceUrl());
        // KPI e RegisteredByUser precisam de tratamento adicional
        return entity;
    }
}
