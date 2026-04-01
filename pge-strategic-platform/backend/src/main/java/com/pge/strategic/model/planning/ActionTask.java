package com.pge.strategic.model.planning;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

/**
 * Tarefa do Plano de Ação
 * Subdivisões das ações para melhor acompanhamento
 */
@Entity
@Table(name = "action_task")
@Data
public class ActionTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "action_plan_id", nullable = false)
    private ActionPlan actionPlan;

    @Column(nullable = false, length = 500)
    private String title;

    @Column(length = 2000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column
    private TaskStatus status = TaskStatus.PENDING;

    @Column
    private Integer priority = 3;

    @Column
    private LocalDate startDate;

    @Column
    private LocalDate dueDate;

    @Column
    private LocalDate completionDate;

    @Column(precision = 5, scale = 2)
    private Double progressPercentage = 0.0;

    @Column(precision = 15, scale = 2)
    private Double estimatedHours;

    @Column(precision = 15, scale = 2)
    private Double actualHours;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responsible_id")
    private AppUser responsible;

    @Column(length = 2000)
    private String comments;

    @Column
    private Boolean critical = false;

    @Column(length = 1000)
    private String deliverables;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDate.now();
    }

    @Column(nullable = false, updatable = false)
    private LocalDate createdAt;
}

/**
 * Status da Tarefa
 */
enum TaskStatus {
    PENDING,        // Pendente
    IN_PROGRESS,    // Em andamento
    BLOCKED,        // Bloqueada
    COMPLETED,      // Concluída
    CANCELLED       // Cancelada
}
