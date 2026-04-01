package com.pge.strategic.controller.planning;

import com.pge.strategic.dto.planning.KpiDTO;
import com.pge.strategic.service.planning.KpiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/planning/kpis")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class KpiController {

    private final KpiService service;

    @GetMapping("/objective/{objectiveId}")
    public ResponseEntity<List<KpiDTO>> findByObjectiveId(@PathVariable Long objectiveId) {
        return ResponseEntity.ok(service.findByObjectiveId(objectiveId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<KpiDTO> findById(@PathVariable Long id) {
        KpiDTO dto = service.findById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<KpiDTO> create(@RequestBody KpiDTO dto) {
        KpiDTO created = service.create(dto);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<KpiDTO> update(@PathVariable Long id, @RequestBody KpiDTO dto) {
        KpiDTO updated = service.update(id, dto);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
