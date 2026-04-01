package com.pge.strategic.model.planning;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidade que representa o Planejamento Estratégico do órgão
 * Contém todas as informações do ciclo de planejamento
 */
@Entity
@Table(name = "strategic_plan")
@Data
@EqualsAndHashCode(callSuper = true)
public class StrategicPlan extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private Integer startYear;

    @Column(nullable = false)
    private Integer endYear;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PlanStatus status = PlanStatus.DRAFT;

    @Column(length = 500)
    private String version;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_id")
    private AppUser createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approved_by_id")
    private AppUser approvedBy;

    @Column
    private LocalDate approvalDate;

    @OneToMany(mappedBy = "strategicPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SwotAnalysis> swotAnalyses = new ArrayList<>();

    @OneToMany(mappedBy = "strategicPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StrategicAxis> strategicAxes = new ArrayList<>();

    @OneToMany(mappedBy = "strategicPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BscPerspective> bscPerspectives = new ArrayList<>();

    @OneToMany(mappedBy = "strategicPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StrategicMapElement> strategicMapElements = new ArrayList<>();

    @OneToMany(mappedBy = "strategicPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FinalDocument> finalDocuments = new ArrayList<>();

    @Column(length = 2000)
    private String institutionalIdentity;

    @Column(length = 2000)
    private String mission;

    @Column(length = 2000)
    private String vision;

    @Column(length = 2000)
    private String values;

    @Column(length = 500)
    private String slogan;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDate.now();
    }
}
