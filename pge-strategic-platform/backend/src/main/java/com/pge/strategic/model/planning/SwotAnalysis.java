package com.pge.strategic.model.planning;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Análise SWOT (FOFA) - Forças, Oportunidades, Fraquezas, Ameaças
 * Utilizada para Análise Interna e Externa no planejamento estratégico
 */
@Entity
@Table(name = "swot_analysis")
@Data
@EqualsAndHashCode(callSuper = true)
public class SwotAnalysis extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "strategic_plan_id", nullable = false)
    private StrategicPlan strategicPlan;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SwotType type;

    @Column(nullable = false, length = 500)
    private String title;

    @Column(length = 2000)
    private String description;

    @Column
    private Integer priority;

    @Column(length = 1000)
    private String impactAnalysis;

    @Column(length = 1000)
    private String actionProposal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responsible_id")
    private AppUser responsible;

    @Column
    private Boolean verified = false;

    @Column(length = 2000)
    private String comments;
}

/**
 * Tipos de análise SWOT
 */
enum SwotType {
    STRENGTH,      // Força (Análise Interna - Positiva)
    WEAKNESS,      // Fraqueza (Análise Interna - Negativa)
    OPPORTUNITY,   // Oportunidade (Análise Externa - Positiva)
    THREAT         // Ameaça (Análise Externa - Negativa)
}
