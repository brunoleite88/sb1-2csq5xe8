package com.pge.strategic.controller.planning;

import com.pge.strategic.dto.planning.KpiMeasurementDTO;
import com.pge.strategic.service.planning.KpiMeasurementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/planning/kpi-measurements")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class KpiMeasurementController {

    private final KpiMeasurementService service;

    @GetMapping("/kpi/{kpiId}")
    public ResponseEntity<List<KpiMeasurementDTO>> findByKpiId(@PathVariable Long kpiId) {
        return ResponseEntity.ok(service.findByKpiId(kpiId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<KpiMeasurementDTO> findById(@PathVariable Long id) {
        KpiMeasurementDTO dto = service.findById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<KpiMeasurementDTO> create(@RequestBody KpiMeasurementDTO dto) {
        KpiMeasurementDTO created = service.create(dto);
        return ResponseEntity.ok(created);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
