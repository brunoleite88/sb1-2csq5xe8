package com.pge.strategic.model.planning;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Medição de KPI - Histórico de medições dos indicadores
 */
@Entity
@Table(name = "kpi_measurement")
@Data
public class KpiMeasurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kpi_id", nullable = false)
    private Kpi kpi;

    @Column(nullable = false)
    private LocalDate measurementDate;

    @Column(precision = 10, scale = 4)
    private BigDecimal measuredValue;

    @Column(precision = 10, scale = 4)
    private BigDecimal targetValue;

    @Column(precision = 5, scale = 2)
    private BigDecimal variancePercentage;

    @Column(length = 2000)
    private String observations;

    @Column(length = 1000)
    private String evidenceUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "measured_by_id")
    private AppUser measuredBy;

    @Column
    private Boolean verified = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "verified_by_id")
    private AppUser verifiedBy;

    @Column
    private LocalDate verificationDate;

    @Column
    private String status = "PENDING";

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDate.now();
    }

    @Column(nullable = false, updatable = false)
    private LocalDate createdAt;
}
