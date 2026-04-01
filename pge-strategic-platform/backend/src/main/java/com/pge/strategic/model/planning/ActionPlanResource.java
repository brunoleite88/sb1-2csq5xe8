package com.pge.strategic.model.planning;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

/**
 * Recurso do Plano de Ação
 * Recursos necessários para execução das ações
 */
@Entity
@Table(name = "action_plan_resource")
@Data
public class ActionPlanResource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "action_plan_id", nullable = false)
    private ActionPlan actionPlan;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ResourceType type = ResourceType.HUMAN;

    @Column
    private Integer quantity = 1;

    @Column(length = 100)
    private String unit;

    @Column(precision = 15, scale = 2)
    private BigDecimal unitCost;

    @Column(precision = 15, scale = 2)
    private BigDecimal totalCost;

    @Column
    private Boolean allocated = false;

    @Column(length = 1000)
    private String observations;
}

/**
 * Tipo de Recurso
 */
enum ResourceType {
    HUMAN,              // Recurso Humano
    FINANCIAL,          // Financeiro
    MATERIAL,           // Material
    EQUIPMENT,          // Equipamento
    TECHNOLOGY,         // Tecnologia
    INFRASTRUCTURE,     // Infraestrutura
    EXTERNAL_SERVICE,   // Serviço Externo
    TRAINING            // Treinamento/Capacitação
}
