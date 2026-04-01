package com.pge.strategic.model.planning;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

/**
 * Perspectiva do Balanced Scorecard (BSC)
 * Representa as 4 perspectivas clássicas ou customizadas
 */
@Entity
@Table(name = "bsc_perspective")
@Data
@EqualsAndHashCode(callSuper = true)
public class BscPerspective extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "strategic_plan_id", nullable = false)
    private StrategicPlan strategicPlan;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BscPerspectiveType type;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(length = 2000)
    private String description;

    @Column
    private Integer orderIndex;

    @Column(length = 1000)
    private String strategicFocus;

    @OneToMany(mappedBy = "bscPerspective", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StrategicObjective> objectives = new ArrayList<>();

    @Column(length = 2000)
    private String customizationNotes;
}

/**
 * Tipos de perspectivas do BSC
 */
enum BscPerspectiveType {
    FINANCIAL,              // Financeira
    CUSTOMER,               // Cliente/Cidadão
    INTERNAL_PROCESSES,     // Processos Internos
    LEARNING_GROWTH,        // Aprendizado e Crescimento
    CUSTOM                  // Personalizada (customizável pelo administrador)
}
