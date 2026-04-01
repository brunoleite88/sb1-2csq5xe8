package com.pge.strategic.model;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entidade de Competência Legal
 * 
 * Representa as competências legais das unidades organizacionais
 * baseadas em leis, decretos, portarias e outros atos normativos.
 */
@Entity
@Table(name = "legal_competency",
       indexes = @Index(name = "idx_legal_competency_unit", columnList = "organ_unit_id"))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class LegalCompetency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Título da competência
     */
    @Column(name = "title", nullable = false, length = 500)
    private String title;

    /**
     * Descrição detalhada da competência
     */
    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    /**
     * Tipo do documento legal
     * LEI, DECRETO, PORTARIA, RESOLUCAO, INSTRUCAO_NORMATIVA
     */
    @Column(name = "legal_document_type", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private LegalDocumentType legalDocumentType = LegalDocumentType.LEI;

    /**
     * Número do documento legal
     */
    @Column(name = "legal_document_number", length = 100)
    private String legalDocumentNumber;

    /**
     * Data do documento legal
     */
    @Column(name = "legal_document_date")
    private LocalDate legalDocumentDate;

    /**
     * Unidade organizacional responsável
     */
    @Column(name = "organ_unit_id", nullable = false)
    private Long organUnitId;

    /**
     * Status da competência
     */
    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;

    /**
     * Data de criação
     */
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    /**
     * Tipos de documentos legais
     */
    public enum LegalDocumentType {
        LEI("Lei"),
        DECRETO("Decreto"),
        PORTARIA("Portaria"),
        RESOLUCAO("Resolução"),
        INSTRUCAO_NORMATIVA("Instrução Normativa"),
        MEDIDA_PROVISORIA("Medida Provisória"),
        EMENDA_CONSTITUCIONAL("Emenda Constitucional");

        private final String description;

        LegalDocumentType(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }
}
