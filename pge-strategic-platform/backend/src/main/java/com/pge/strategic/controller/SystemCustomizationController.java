package com.pge.strategic.controller;

import com.pge.strategic.model.SystemCustomization;
import com.pge.strategic.service.SystemCustomizationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * Controller para gerenciamento de customizações do sistema
 * 
 * Permite que administradores personalizem completamente
 * rótulos, mensagens, títulos e configurações do sistema.
 */
@RestController
@RequestMapping("/api/customizations")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Customizações", description = "API para gerenciamento de customizações do sistema")
public class SystemCustomizationController {

    private final SystemCustomizationService service;

    /**
     * Lista todas as customizações ativas
     */
    @GetMapping
    @Operation(summary = "Listar customizações ativas", description = "Retorna todas as customizações ativas do sistema")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROCURADOR_GERAL', 'REGIONAL')")
    public ResponseEntity<List<SystemCustomization>> findAllActive() {
        log.info("Requisição para listar todas as customizações ativas");
        return ResponseEntity.ok(service.findAllActive());
    }

    /**
     * Lista customizações por categoria com paginação
     */
    @GetMapping("/category/{category}")
    @Operation(summary = "Listar por categoria", description = "Retorna customizações de uma categoria específica com paginação")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROCURADOR_GERAL', 'REGIONAL')")
    public ResponseEntity<Page<SystemCustomization>> findByCategory(
            @PathVariable String category,
            Pageable pageable) {
        log.info("Requisição para listar customizações da categoria: {}", category);
        return ResponseEntity.ok(service.findByCategory(category, pageable));
    }

    /**
     * Busca uma customização pela chave
     */
    @GetMapping("/{key}")
    @Operation(summary = "Buscar por chave", description = "Retorna uma customização específica pela chave")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROCURADOR_GERAL', 'REGIONAL', 'PROCURADOR', 'ANALISTA')")
    public ResponseEntity<SystemCustomization> findByKey(@PathVariable String key) {
        log.info("Requisição para buscar customização pela chave: {}", key);
        return service.findByKey(key)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Obtém o valor de uma customização pela chave
     */
    @GetMapping("/value/{key}")
    @Operation(summary = "Obter valor por chave", description = "Retorna apenas o valor de uma customização")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROCURADOR_GERAL', 'REGIONAL', 'PROCURADOR', 'ANALISTA', 'VISUALIZADOR')")
    public ResponseEntity<String> getValueByKey(
            @PathVariable String key,
            @RequestParam(defaultValue = "") String defaultValue) {
        log.info("Requisição para obter valor da customização: {}", key);
        String value = service.getValueByKey(key, defaultValue);
        return ResponseEntity.ok(value);
    }

    /**
     * Lista todas as customizações agrupadas por categoria
     */
    @GetMapping("/grouped-by-category")
    @Operation(summary = "Agrupar por categoria", description = "Retorna todas as customizações agrupadas por categoria")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROCURADOR_GERAL')")
    public ResponseEntity<Map<String, List<SystemCustomization>>> getAllGroupedByCategory() {
        log.info("Requisição para listar customizações agrupadas por categoria");
        return ResponseEntity.ok(service.getAllGroupedByCategory());
    }

    /**
     * Cria ou atualiza uma customização
     */
    @PostMapping
    @Operation(summary = "Salvar customização", description = "Cria ou atualiza uma customização do sistema")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SystemCustomization> saveOrUpdate(
            @Valid @RequestBody SystemCustomization customization) {
        log.info("Requisição para salvar customização: {}", customization.getCustomizationKey());
        
        // Valida a chave
        if (!service.isValidKey(customization.getCustomizationKey())) {
            return ResponseEntity.badRequest().build();
        }
        
        SystemCustomization saved = service.saveOrUpdate(customization);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /**
     * Atualiza uma customização existente
     */
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar customização", description = "Atualiza uma customização existente")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SystemCustomization> update(
            @PathVariable Long id,
            @Valid @RequestBody SystemCustomization customization) {
        log.info("Requisição para atualizar customização ID: {}", id);
        
        customization.setId(id);
        SystemCustomization updated = service.saveOrUpdate(customization);
        return ResponseEntity.ok(updated);
    }

    /**
     * Desativa uma customização (soft delete)
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Desativar customização", description = "Desativa uma customização (soft delete)")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        log.info("Requisição para desativar customização ID: {}", id);
        service.deactivate(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Deleta permanentemente uma customização
     */
    @DeleteMapping("/{id}/permanent")
    @Operation(summary = "Deletar permanentemente", description = "Deleta permanentemente uma customização")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletePermanent(@PathVariable Long id) {
        log.info("Requisição para deletar permanentemente customização ID: {}", id);
        service.deletePermanent(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Busca customizações por termo
     */
    @GetMapping("/search")
    @Operation(summary = "Buscar por termo", description = "Busca customizações por termo na chave")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROCURADOR_GERAL', 'REGIONAL')")
    public ResponseEntity<List<SystemCustomization>> searchByTerm(
            @RequestParam String term) {
        log.info("Requisição para buscar customizações pelo termo: {}", term);
        return ResponseEntity.ok(service.searchByTerm(term));
    }

    /**
     * Carrega todas as customizações como mapa (chave-valor)
     */
    @GetMapping("/all-as-map")
    @Operation(summary = "Todas como mapa", description = "Retorna todas as customizações como mapa chave-valor")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROCURADOR_GERAL', 'REGIONAL')")
    public ResponseEntity<Map<String, String>> loadAllAsMap() {
        log.info("Requisição para carregar todas as customizações como mapa");
        return ResponseEntity.ok(service.loadAllAsMap());
    }
}
