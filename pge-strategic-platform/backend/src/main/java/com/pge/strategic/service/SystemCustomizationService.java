package com.pge.strategic.service;

import com.pge.strategic.model.SystemCustomization;
import com.pge.strategic.repository.SystemCustomizationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Serviço para gerenciamento de customizações do sistema
 * 
 * Permite que administradores personalizem completamente
 * rótulos, mensagens, títulos e configurações.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SystemCustomizationService {

    private final SystemCustomizationRepository repository;

    /**
     * Busca todas as customizações ativas
     */
    @Transactional(readOnly = true)
    public List<SystemCustomization> findAllActive() {
        log.debug("Buscando todas as customizações ativas");
        return repository.findByIsActiveTrue();
    }

    /**
     * Busca uma customização pela chave
     */
    @Transactional(readOnly = true)
    public Optional<SystemCustomization> findByKey(String key) {
        log.debug("Buscando customização pela chave: {}", key);
        return repository.findByCustomizationKey(key);
    }

    /**
     * Busca o valor de uma customização pela chave
     * Retorna um valor default se não encontrar
     */
    @Transactional(readOnly = true)
    public String getValueByKey(String key, String defaultValue) {
        return repository.findByCustomizationKey(key)
                .map(SystemCustomization::getCustomizationValue)
                .orElse(defaultValue);
    }

    /**
     * Busca customizações por categoria
     */
    @Transactional(readOnly = true)
    public Page<SystemCustomization> findByCategory(String category, Pageable pageable) {
        log.debug("Buscando customizações da categoria: {}", category);
        return repository.findByCategoryAndIsActiveTrue(category, pageable);
    }

    /**
     * Busca todas as customizações de uma categoria
     */
    @Transactional(readOnly = true)
    public List<SystemCustomization> findAllByCategory(String category) {
        log.debug("Buscando todas as customizações da categoria: {}", category);
        return repository.findByCategoryOrderByCustomizationKey(category);
    }

    /**
     * Retorna todas as customizações agrupadas por categoria
     */
    @Transactional(readOnly = true)
    public Map<String, List<SystemCustomization>> getAllGroupedByCategory() {
        log.debug("Agrupando customizações por categoria");
        return findAllActive().stream()
                .collect(Collectors.groupingBy(SystemCustomization::getCategory));
    }

    /**
     * Cria ou atualiza uma customização
     */
    @Transactional
    public SystemCustomization saveOrUpdate(SystemCustomization customization) {
        log.info("Salvando customização: {}", customization.getCustomizationKey());
        
        // Verifica se já existe
        Optional<SystemCustomization> existing = repository.findByCustomizationKey(
                customization.getCustomizationKey()
        );
        
        if (existing.isPresent()) {
            SystemCustomization toUpdate = existing.get();
            toUpdate.setCustomizationValue(customization.getCustomizationValue());
            toUpdate.setCategory(customization.getCategory());
            toUpdate.setDescription(customization.getDescription());
            toUpdate.setIsActive(customization.getIsActive());
            toUpdate.setCreatedBy(customization.getCreatedBy());
            
            log.info("Atualizando customização existente: {}", customization.getCustomizationKey());
            return repository.save(toUpdate);
        } else {
            log.info("Criando nova customização: {}", customization.getCustomizationKey());
            return repository.save(customization);
        }
    }

    /**
     * Deleta uma customização (soft delete - desativa)
     */
    @Transactional
    public void deactivate(Long id) {
        log.info("Desativando customização ID: {}", id);
        repository.findById(id).ifPresent(customization -> {
            customization.setIsActive(false);
            repository.save(customization);
        });
    }

    /**
     * Deleta permanentemente uma customização
     */
    @Transactional
    public void deletePermanent(Long id) {
        log.info("Deletando permanentemente customização ID: {}", id);
        repository.deleteById(id);
    }

    /**
     * Busca customizações por termo
     */
    @Transactional(readOnly = true)
    public List<SystemCustomization> searchByTerm(String term) {
        log.debug("Buscando customizações pelo termo: {}", term);
        return repository.searchByTerm(term);
    }

    /**
     * Valida se uma chave de customização é válida
     */
    public boolean isValidKey(String key) {
        return key != null && !key.trim().isEmpty() && key.matches("^[A-Z0-9_]+$");
    }

    /**
     * Carrega todas as customizações em cache (para uso em filtros)
     */
    @Transactional(readOnly = true)
    public Map<String, String> loadAllAsMap() {
        return findAllActive().stream()
                .collect(Collectors.toMap(
                        SystemCustomization::getCustomizationKey,
                        SystemCustomization::getCustomizationValue
                ));
    }
}
