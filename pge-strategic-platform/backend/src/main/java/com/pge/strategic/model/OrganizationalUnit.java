package com.pge.strategic.model;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entidade de Unidade Organizacional
 * 
 * Representa a estrutura organizacional da PGE:
 * Procuradorias Regionais, Setoriais, Núcleos, Coordenações, Gabinetes
 */
@Entity
@Table(name = "organizational_unit",
       indexes = {
           @Index(name = "idx_organizational_unit_parent", columnList = "parent_unit_id"),
           @Index(name = "idx_organizational_unit_type", columnList = "unit_type")
       })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class OrganizationalUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nome da unidade
     */
    @Column(name = "name", nullable = false, length = 255)
    private String name;

    /**
     * Código identificador único
     */
    @Column(name = "code", nullable = false, length = 50, unique = true)
    private String code;

    /**
     * Tipo da unidade
     * REGIONAL, SETORIAL, NUCLEO, COORDENACAO, GABINETE
     */
    @Column(name = "unit_type", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private UnitType unitType = UnitType.NUCLEO;

    /**
     * Unidade pai (hierarquia)
     */
    @Column(name = "parent_unit_id")
    private Long parentUnitId;

    /**
     * Descrição detalhada
     */
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    /**
     * Fundamento legal (lei, decreto, portaria)
     */
    @Column(name = "legal_basis", columnDefinition = "TEXT")
    private String legalBasis;

    /**
     * Status da unidade
     */
    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;

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

    /**
     * Tipos de unidades organizacionais
     */
    public enum UnitType {
        REGIONAL("Procuradoria Regional"),
        SETORIAL("Procuradoria Setorial"),
        NUCLEO("Núcleo Especializado"),
        COORDENACAO("Coordenação"),
        GABINETE("Gabinete"),
        PROCURADORIA_GERAL("Procuradoria Geral");

        private final String description;

        UnitType(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }
}
