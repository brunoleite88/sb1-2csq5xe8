package com.pge.modules.riscos.controller;

import com.pge.modules.riscos.dto.RiscoIdentificadoDTO;
import com.pge.modules.riscos.service.RiscoIdentificadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST para Gestão de Riscos Identificados
 */
@RestController
@RequestMapping("/api/riscos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RiscoIdentificadoController {
    
    private final RiscoIdentificadoService service;
    
    @GetMapping
    public ResponseEntity<List<RiscoIdentificadoDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<RiscoIdentificadoDTO> buscarPorId(@PathVariable Long id) {
        RiscoIdentificadoDTO risco = service.buscarPorId(id);
        return risco != null ? ResponseEntity.ok(risco) : ResponseEntity.notFound().build();
    }
    
    @GetMapping("/ata/{ataId}")
    public ResponseEntity<List<RiscoIdentificadoDTO>> listarPorAta(@PathVariable Long ataId) {
        return ResponseEntity.ok(service.listarPorAta(ataId));
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<RiscoIdentificadoDTO>> listarPorStatus(@PathVariable String status) {
        return ResponseEntity.ok(service.listarPorStatus(status));
    }
    
    @PostMapping
    public ResponseEntity<RiscoIdentificadoDTO> salvar(@RequestBody RiscoIdentificadoDTO dto) {
        RiscoIdentificadoDTO salvo = service.salvar(dto);
        return ResponseEntity.ok(salvo);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<RiscoIdentificadoDTO> atualizar(@PathVariable Long id, @RequestBody RiscoIdentificadoDTO dto) {
        dto.setId(id);
        RiscoIdentificadoDTO atualizado = service.salvar(dto);
        return ResponseEntity.ok(atualizado);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
