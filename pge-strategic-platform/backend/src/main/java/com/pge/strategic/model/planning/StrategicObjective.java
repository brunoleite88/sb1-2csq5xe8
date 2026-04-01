package com.pge.strategic.model.planning;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Objetivo Estratégico
 * Representa os objetivos do planejamento vinculados a eixos e perspectivas BSC
 */
@Entity
@Table(name = "strategic_objective")
@Data
@EqualsAndHashCode(callSuper = true)
public class StrategicObjective extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "strategic_plan_id", nullable = false)
    private StrategicPlan strategicPlan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "strategic_axis_id")
    private StrategicAxis strategicAxis;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bsc_perspective_id")
    private BscPerspective bscPerspective;

    @Column(nullable = false, length = 200)
    private String code;

    @Column(nullable = false, length = 500)
    private String title;

    @Column(length = 2000)
    private String description;

    @Column
    private Integer orderIndex;

    @Enumerated(EnumType.STRING)
    @Column
    private ObjectiveStatus status = ObjectiveStatus.PLANNED;

    @Column(length = 1000)
    private String successCriteria;

    @Column(length = 1000)
    private String assumptions;

    @Column(length = 1000)
    private String constraints;

    @OneToMany(mappedBy = "strategicObjective", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<KeyResult> keyResults = new ArrayList<>();

    @OneToMany(mappedBy = "strategicObjective", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Kpi> kpis = new ArrayList<>();

    @OneToMany(mappedBy = "strategicObjective", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ActionPlan> actionPlans = new ArrayList<>();

    @OneToMany(mappedBy = "strategicObjective", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ObjectiveDependency> dependencies = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private AppUser owner;

    @Column
    private LocalDate targetDate;

    @Column(precision = 5, scale = 2)
    private BigDecimal targetValue;

    @Column(length = 2000)
    private String comments;
}

/**
 * Status do Objetivo Estratégico
 */
enum ObjectiveStatus {
    PLANNED,        // Planejado
    IN_PROGRESS,    // Em andamento
    ON_TRACK,       // No prazo
    AT_RISK,        // Em risco
    DELAYED,        // Atrasado
    COMPLETED,      // Concluído
    CANCELLED       // Cancelado
}
