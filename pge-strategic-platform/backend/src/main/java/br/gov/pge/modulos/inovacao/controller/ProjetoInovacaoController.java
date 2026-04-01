package br.gov.pge.modulos.inovacao.controller;

import br.gov.pge.modulos.inovacao.entity.ProjetoInovacao;
import br.gov.pge.modulos.inovacao.entity.EtapaInovacao;
import br.gov.pge.modulos.inovacao.service.ProjetoInovacaoService;
import br.gov.pge.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/inovacao/projetos")
@RequiredArgsConstructor
public class ProjetoInovacaoController {
    
    private final ProjetoInovacaoService service;
    
    @GetMapping
    @PreAuthorize("hasAuthority('INOVACAO_VISUALIZAR')")
    public ResponseEntity<List<ProjetoInovacao>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('INOVACAO_VISUALIZAR')")
    public ResponseEntity<ProjetoInovacao> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }
    
    @GetMapping("/etapa/{etapa}")
    @PreAuthorize("hasAuthority('INOVACAO_VISUALIZAR')")
    public ResponseEntity<List<ProjetoInovacao>> listarPorEtapa(@PathVariable EtapaInovacao etapa) {
        return ResponseEntity.ok(service.listarPorEtapa(etapa));
    }
    
    @GetMapping("/meus-projetos")
    @PreAuthorize("hasAuthority('INOVACAO_VISUALIZAR')")
    public ResponseEntity<List<ProjetoInovacao>> listarMeusProjetos(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(service.listarPorResponsavel(userDetails.getId()));
    }
    
    @GetMapping("/estatisticas")
    @PreAuthorize("hasAuthority('INOVACAO_VISUALIZAR')")
    public ResponseEntity<List<Object[]>> obterEstatisticas() {
        return ResponseEntity.ok(service.obterEstatisticasPorEtapa());
    }
    
    @PostMapping
    @PreAuthorize("hasAuthority('INOVACAO_CRIAR')")
    public ResponseEntity<ProjetoInovacao> criar(
            @RequestBody ProjetoInovacao projeto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        
        projeto.setResponsavel(userDetails.getUser());
        ProjetoInovacao novoProjeto = service.criar(projeto);
        return ResponseEntity.created(URI.create("/api/inovacao/projetos/" + novoProjeto.getId()))
                .body(novoProjeto);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('INOVACAO_EDITAR')")
    public ResponseEntity<ProjetoInovacao> atualizar(
            @PathVariable Long id,
            @RequestBody ProjetoInovacao projeto) {
        return ResponseEntity.ok(service.atualizar(id, projeto));
    }
    
    @PatchMapping("/{id}/avancar-etapa")
    @PreAuthorize("hasAuthority('INOVACAO_EDITAR')")
    public ResponseEntity<Void> avancarEtapa(@PathVariable Long id) {
        service.avancarEtapa(id);
        return ResponseEntity.noContent().build();
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('INOVACAO_EXCLUIR')")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluirLogico(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/premio/indicados")
    @PreAuthorize("hasAuthority('INOVACAO_VISUALIZAR')")
    public ResponseEntity<List<ProjetoInovacao>> listarIndicadosPremio() {
        return ResponseEntity.ok(service.listarIndicadosPremio());
    }
}
