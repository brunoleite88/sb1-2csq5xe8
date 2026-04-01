package com.pge.strategic.model.planning;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.ArrayList;
import java.util.List;

/**
 * Eixo Estratégico do Planejamento
 * Representa as grandes áreas de atuação estratégica do órgão
 */
@Entity
@Table(name = "strategic_axis")
@Data
@EqualsAndHashCode(callSuper = true)
public class StrategicAxis extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "strategic_plan_id", nullable = false)
    private StrategicPlan strategicPlan;

    @Column(nullable = false, length = 200)
    private String code;

    @Column(nullable = false, length = 500)
    private String title;

    @Column(length = 2000)
    private String description;

    @Column
    private Integer orderIndex;

    @Column(length = 1000)
    private String justification;

    @OneToMany(mappedBy = "strategicAxis", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StrategicObjective> objectives = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coordinator_id")
    private AppUser coordinator;

    @Column(length = 2000)
    private String expectedResults;
}
