package com.pge.strategic.model.planning;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Plano de Ação
 * Ações específicas para alcançar objetivos estratégicos
 */
@Entity
@Table(name = "action_plan")
@Data
@EqualsAndHashCode(callSuper = true)
public class ActionPlan extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "strategic_objective_id", nullable = false)
    private StrategicObjective strategicObjective;

    @Column(nullable = false, length = 500)
    private String title;

    @Column(length = 2000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ActionType type = ActionType.INITIATIVE;

    @Enumerated(EnumType.STRING)
    @Column
    private ActionStatus status = ActionStatus.PLANNED;

    @Column
    private Integer priority = 3;

    @Column
    private LocalDate startDate;

    @Column
    private LocalDate dueDate;

    @Column
    private LocalDate completionDate;

    @Column(precision = 15, scale = 2)
    private Double estimatedBudget;

    @Column(precision = 15, scale = 2)
    private Double actualBudget;

    @Column(precision = 5, scale = 2)
    private Double progressPercentage = 0.0;

    @OneToMany(mappedBy = "actionPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ActionTask> tasks = new ArrayList<>();

    @OneToMany(mappedBy = "actionPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ActionPlanResource> resources = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private AppUser owner;

    @ManyToMany
    @JoinTable(
        name = "action_plan_team",
        joinColumns = @JoinColumn(name = "action_plan_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<AppUser> team = new ArrayList<>();

    @Column(length = 2000)
    private String deliverables;

    @Column(length = 2000)
    private String risks;

    @Column(length = 2000)
    private String comments;

    @Column
    private Boolean critical = false;

    @Column(length = 1000)
    private String successCriteria;
}

/**
 * Tipo de Ação
 */
enum ActionType {
    INITIATIVE,         // Iniciativa
    PROJECT,            // Projeto
    ACTIVITY,           // Atividade
    PROCESS_IMPROVEMENT,// Melhoria de Processo
    TRAINING,           // Treinamento
    TECHNOLOGY,         // Tecnologia
    REGULATORY          // Regulamentação/Normativo
}

/**
 * Status do Plano de Ação
 */
enum ActionStatus {
    PLANNED,        // Planejado
    APPROVED,       // Aprovado
    IN_PROGRESS,    // Em andamento
    ON_HOLD,        // Suspenso
    COMPLETED,      // Concluído
    CANCELLED       // Cancelado
}
