package com.pge.strategic.repository;

import com.pge.strategic.model.SystemCustomization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositório para SystemCustomization
 * 
 * Permite operações CRUD e consultas personalizadas
 * para customização do sistema.
 */
@Repository
public interface SystemCustomizationRepository extends JpaRepository<SystemCustomization, Long> {

    /**
     * Busca uma customização pela chave
     */
    Optional<SystemCustomization> findByCustomizationKey(String customizationKey);

    /**
     * Busca todas as customizações ativas
     */
    List<SystemCustomization> findByIsActiveTrue();

    /**
     * Busca customizações por categoria
     */
    Page<SystemCustomization> findByCategoryAndIsActiveTrue(String category, Pageable pageable);

    /**
     * Busca todas as customizações de uma categoria (ativas e inativas)
     */
    List<SystemCustomization> findByCategoryOrderByCustomizationKey(String category);

    /**
     * Verifica se existe uma customização com a chave informada
     */
    boolean existsByCustomizationKey(String customizationKey);

    /**
     * Busca customizações por chave contendo o termo (busca parcial)
     */
    @Query("SELECT sc FROM SystemCustomization sc WHERE LOWER(sc.customizationKey) LIKE LOWER(CONCAT('%', :term, '%')) AND sc.isActive = true")
    List<SystemCustomization> searchByTerm(@Param("term") String term);

    /**
     * Conta customizações por categoria
     */
    long countByCategoryAndIsActiveTrue(String category);

    /**
     * Desativa todas as customizações de uma categoria
     */
    @Query("UPDATE SystemCustomization sc SET sc.isActive = false WHERE sc.category = :category")
    void deactivateByCategory(@Param("category") String category);
}
