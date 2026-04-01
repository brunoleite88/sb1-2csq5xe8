package com.pge.strategic.model.planning;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Dependência entre Objetivos Estratégicos
 * Relaciona objetivos que possuem dependências entre si
 */
@Entity
@Table(name = "objective_dependency")
@Data
public class ObjectiveDependency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "strategic_objective_id", nullable = false)
    private StrategicObjective strategicObjective;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dependent_objective_id", nullable = false)
    private StrategicObjective dependentObjective;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DependencyType type = DependencyType.FINISH_TO_START;

    @Column(length = 1000)
    private String description;

    @Column
    private Integer lagDays = 0;

    @Column
    private Boolean critical = false;

    @Column(length = 2000)
    private String impactAnalysis;
}

/**
 * Tipos de Dependência
 */
enum DependencyType {
    FINISH_TO_START,    // Término-Início (FS)
    START_TO_START,     // Início-Início (SS)
    FINISH_TO_FINISH,   // Término-Término (FF)
    START_TO_FINISH     // Início-Término (SF)
}
