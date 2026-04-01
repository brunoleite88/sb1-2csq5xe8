package com.pge.modules.contratos.controller;

import com.pge.modules.contratos.dto.ContratoDTO;
import com.pge.modules.contratos.service.ContratoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST para Gestão de Contratos
 */
@RestController
@RequestMapping("/api/contratos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ContratoController {
    
    private final ContratoService service;
    
    @GetMapping
    public ResponseEntity<List<ContratoDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ContratoDTO> buscarPorId(@PathVariable Long id) {
        ContratoDTO contrato = service.buscarPorId(id);
        return contrato != null ? ResponseEntity.ok(contrato) : ResponseEntity.notFound().build();
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<ContratoDTO>> listarPorStatus(@PathVariable String status) {
        return ResponseEntity.ok(service.listarPorStatus(status));
    }
    
    @GetMapping("/vencendo/{dias}")
    public ResponseEntity<List<ContratoDTO>> listarVencendoProximamente(@PathVariable int dias) {
        return ResponseEntity.ok(service.listarVencendoProximamente(dias));
    }
    
    @PostMapping
    public ResponseEntity<ContratoDTO> salvar(@RequestBody ContratoDTO dto) {
        ContratoDTO salvo = service.salvar(dto);
        return ResponseEntity.ok(salvo);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ContratoDTO> atualizar(@PathVariable Long id, @RequestBody ContratoDTO dto) {
        dto.setId(id);
        ContratoDTO atualizado = service.salvar(dto);
        return ResponseEntity.ok(atualizado);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
