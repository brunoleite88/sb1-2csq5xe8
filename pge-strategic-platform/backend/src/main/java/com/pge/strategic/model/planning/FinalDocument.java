package com.pge.strategic.model.planning;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDate;

/**
 * Documento Final do Planejamento Estratégico
 * Gera documento consolidado para impressão/exportação
 */
@Entity
@Table(name = "final_document")
@Data
@EqualsAndHashCode(callSuper = true)
public class FinalDocument extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "strategic_plan_id", nullable = false)
    private StrategicPlan strategicPlan;

    @Column(nullable = false, length = 200)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DocumentType type = DocumentType.FULL_PLAN;

    @Enumerated(EnumType.STRING)
    @Column
    private DocumentStatus status = DocumentStatus.DRAFT;

    @Column(length = 1000)
    private String description;

    @Column(length = 500)
    private String filePath;

    @Column(length = 500)
    private String fileName;

    @Column
    private Long fileSize;

    @Column(length = 100)
    private String mimeType;

    @Column
    private LocalDate generatedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "generated_by_id")
    private AppUser generatedBy;

    @Column
    private Boolean includeSwot = true;

    @Column
    private Boolean includeIdentity = true;

    @Column
    private Boolean includeAxes = true;

    @Column
    private Boolean includeBsc = true;

    @Column
    private Boolean includeMap = true;

    @Column
    private Boolean includeObjectives = true;

    @Column
    private Boolean includeIndicators = true;

    @Column
    private Boolean includeActionPlans = true;

    @Column
    private Boolean includeBudget = true;

    @Column(length = 2000)
    private String customIntroduction;

    @Column(length = 2000)
    private String customConclusion;

    @Column(length = 1000)
    private String templateName;

    @Column
    private String coverImage;

    @Column
    private Boolean landscape = false;

    @Column
    private String pageSize = "A4";

    @Column(length = 2000)
    private String comments;

    @Version
    private Long version;
}

/**
 * Tipos de Documento
 */
enum DocumentType {
    FULL_PLAN,              // Plano Completo
    EXECUTIVE_SUMMARY,      // Resumo Executivo
    STRATEGIC_MAP_ONLY,     // Apenas Mapa Estratégico
    BSC_REPORT,             // Relatório BSC
    ACTION_PLANS_REPORT,    // Relatório de Planos de Ação
    KPI_REPORT,             // Relatório de Indicadores
    SWOT_ANALYSIS,          // Análise SWOT
    CUSTOM                  // Personalizado
}

/**
 * Status do Documento
 */
enum DocumentStatus {
    DRAFT,          // Rascunho
    GENERATING,     // Gerando
    READY,          // Pronto
    PUBLISHED,      // Publicado
    ARCHIVED        // Arquivado
}
