package com.pge.strategic.controller.planning;

import com.pge.strategic.dto.planning.StrategicPlanDTO;
import com.pge.strategic.service.planning.StrategicPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/planning/strategic-plans")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class StrategicPlanController {

    private final StrategicPlanService service;

    @GetMapping
    public ResponseEntity<List<StrategicPlanDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StrategicPlanDTO> findById(@PathVariable Long id) {
        StrategicPlanDTO dto = service.findById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<StrategicPlanDTO> create(@RequestBody StrategicPlanDTO dto) {
        StrategicPlanDTO created = service.create(dto);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StrategicPlanDTO> update(@PathVariable Long id, @RequestBody StrategicPlanDTO dto) {
        StrategicPlanDTO updated = service.update(id, dto);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
