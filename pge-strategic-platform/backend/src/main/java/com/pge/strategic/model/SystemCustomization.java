package com.pge.strategic.model;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Entidade de Customização do Sistema
 * 
 * Permite que administradores personalizem completamente
 * rótulos, mensagens, títulos e configurações do sistema.
 */
@Entity
@Table(name = "system_customization", 
       indexes = @Index(name = "idx_customization_category", columnList = "category"),
       uniqueConstraints = @UniqueConstraint(columnNames = "customization_key"))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class SystemCustomization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Chave única da customização
     * Ex: LABEL_PROCESSO, TITLE_DASHBOARD, MESSAGE_WELCOME
     */
    @Column(name = "customization_key", nullable = false, length = 255)
    private String customizationKey;

    /**
     * Valor personalizado
     */
    @Column(name = "customization_value", nullable = false, columnDefinition = "TEXT")
    private String customizationValue;

    /**
     * Categoria da customização
     * LABELS, MESSAGES, TITLES, WORKFLOWS, DASHBOARDS
     */
    @Column(name = "category", length = 100)
    private String category;

    /**
     * Descrição da customização
     */
    @Column(name = "description", length = 500)
    private String description;

    /**
     * Status da customização
     */
    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;

    /**
     * ID do usuário que criou
     */
    @Column(name = "created_by")
    private Long createdBy;

    /**
     * Data de criação
     */
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    /**
     * Data de atualização
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
