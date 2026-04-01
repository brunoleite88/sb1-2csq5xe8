package br.gov.pge.modulos.equidade.controller;

import br.gov.pge.modulos.equidade.entity.IndicadorDiversidade;
import br.gov.pge.modulos.equidade.entity.TipoIndicador;
import br.gov.pge.modulos.equidade.service.IndicadorDiversidadeService;
import br.gov.pge.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/equidade/indicadores")
@RequiredArgsConstructor
public class IndicadorDiversidadeController {
    
    private final IndicadorDiversidadeService service;
    
    @GetMapping
    @PreAuthorize("hasAuthority('EQUIDADE_VISUALIZAR')")
    public ResponseEntity<List<IndicadorDiversidade>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('EQUIDADE_VISUALIZAR')")
    public ResponseEntity<IndicadorDiversidade> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }
    
    @GetMapping("/unidade/{unidadeId}")
    @PreAuthorize("hasAuthority('EQUIDADE_VISUALIZAR')")
    public ResponseEntity<List<IndicadorDiversidade>> listarPorUnidade(@PathVariable Long unidadeId) {
        return ResponseEntity.ok(service.listarPorUnidade(unidadeId));
    }
    
    @GetMapping("/tipo/{tipo}")
    @PreAuthorize("hasAuthority('EQUIDADE_VISUALIZAR')")
    public ResponseEntity<List<IndicadorDiversidade>> listarPorTipo(@PathVariable TipoIndicador tipo) {
        return ResponseEntity.ok(service.listarPorTipo(tipo));
    }
    
    @GetMapping("/periodo")
    @PreAuthorize("hasAuthority('EQUIDADE_VISUALIZAR')")
    public ResponseEntity<List<IndicadorDiversidade>> listarPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        return ResponseEntity.ok(service.listarPorPeriodo(inicio, fim));
    }
    
    @GetMapping("/relatorio-consolidado")
    @PreAuthorize("hasAuthority('EQUIDADE_VISUALIZAR')")
    public ResponseEntity<Map<String, Object>> gerarRelatorioConsolidado(
            @RequestParam(required = false) Long unidadeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime periodoInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime periodoFim) {
        return ResponseEntity.ok(service.gerarRelatorioConsolidado(unidadeId, periodoInicio, periodoFim));
    }
    
    @PostMapping
    @PreAuthorize("hasAuthority('EQUIDADE_CRIAR')")
    public ResponseEntity<IndicadorDiversidade> criar(
            @RequestBody IndicadorDiversidade indicador,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        
        indicador.setResponsavelPreenchimento(userDetails.getUser());
        IndicadorDiversidade novoIndicador = service.criar(indicador);
        return ResponseEntity.created(URI.create("/api/equidade/indicadores/" + novoIndicador.getId()))
                .body(novoIndicador);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('EQUIDADE_EDITAR')")
    public ResponseEntity<IndicadorDiversidade> atualizar(
            @PathVariable Long id,
            @RequestBody IndicadorDiversidade indicador) {
        return ResponseEntity.ok(service.atualizar(id, indicador));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('EQUIDADE_EDITAR')")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluirLogico(id);
        return ResponseEntity.noContent().build();
    }
}
