package com.pge.strategic.controller.planning;

import com.pge.strategic.dto.planning.ActionPlanDTO;
import com.pge.strategic.service.planning.ActionPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/planning/action-plans")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ActionPlanController {

    private final ActionPlanService service;

    @GetMapping("/objective/{objectiveId}")
    public ResponseEntity<List<ActionPlanDTO>> findByObjectiveId(@PathVariable Long objectiveId) {
        return ResponseEntity.ok(service.findByObjectiveId(objectiveId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActionPlanDTO> findById(@PathVariable Long id) {
        ActionPlanDTO dto = service.findById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ActionPlanDTO> create(@RequestBody ActionPlanDTO dto) {
        ActionPlanDTO created = service.create(dto);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActionPlanDTO> update(@PathVariable Long id, @RequestBody ActionPlanDTO dto) {
        ActionPlanDTO updated = service.update(id, dto);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
