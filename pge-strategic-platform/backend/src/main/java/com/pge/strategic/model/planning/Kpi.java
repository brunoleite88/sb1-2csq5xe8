package com.pge.strategic.model.planning;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * KPI - Key Performance Indicator (Indicador Chave de Desempenho)
 * Indicadores para monitoramento de objetivos estratégicos
 */
@Entity
@Table(name = "kpi")
@Data
@EqualsAndHashCode(callSuper = true)
public class Kpi extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "strategic_objective_id", nullable = false)
    private StrategicObjective strategicObjective;

    @Column(nullable = false, length = 200)
    private String code;

    @Column(nullable = false, length = 500)
    private String title;

    @Column(length = 2000)
    private String description;

    @Column(length = 500)
    private String formula;

    @Column
    private String unitOfMeasure;

    @Column(precision = 10, scale = 4)
    private BigDecimal currentValue;

    @Column(precision = 10, scale = 4)
    private BigDecimal targetValue;

    @Column(precision = 10, scale = 4)
    private BigDecimal minimumValue;

    @Column(precision = 10, scale = 4)
    private BigDecimal maximumValue;

    @Enumerated(EnumType.STRING)
    @Column
    private KpiFrequency frequency = KpiFrequency.MONTHLY;

    @Enumerated(EnumType.STRING)
    @Column
    private KpiType type = KpiType.QUANTITATIVE;

    @Enumerated(EnumType.STRING)
    @Column
    private KpiDirection direction = KpiDirection.HIGHER_BETTER;

    @Column
    private LocalDate lastMeasurementDate;

    @Column
    private LocalDate nextMeasurementDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private AppUser owner;

    @OneToMany(mappedBy = "kpi", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<KpiMeasurement> measurements = new ArrayList<>();

    @Column(length = 2000)
    private String dataSources;

    @Column(length = 2000)
    private String calculationMethod;

    @Column
    private Boolean active = true;

    @Column
    private Boolean alertEnabled = true;

    @Column(precision = 5, scale = 2)
    private BigDecimal alertThreshold;
}

/**
 * Frequência de medição do KPI
 */
enum KpiFrequency {
    DAILY,
    WEEKLY,
    BIWEEKLY,
    MONTHLY,
    BIMONTHLY,
    QUARTERLY,
    SEMIANNUAL,
    ANNUAL
}

/**
 * Tipo de KPI
 */
enum KpiType {
    QUANTITATIVE,   // Quantitativo
    QUALITATIVE     // Qualitativo
}

/**
 * Direção de melhoria do KPI
 */
enum KpiDirection {
    HIGHER_BETTER,    // Quanto maior, melhor
    LOWER_BETTER,     // Quanto menor, melhor
    TARGET_BETTER     // Quanto mais próximo da meta, melhor
}
