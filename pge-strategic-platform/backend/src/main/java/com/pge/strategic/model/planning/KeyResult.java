package com.pge.strategic.model.planning;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Key Result (Resultado Chave) - OKR
 * Resultados mensuráveis vinculados a objetivos estratégicos
 */
@Entity
@Table(name = "key_result")
@Data
@EqualsAndHashCode(callSuper = true)
public class KeyResult extends BaseEntity {

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

    @Column(nullable = false)
    private BigDecimal targetValue;

    @Column(precision = 10, scale = 4)
    private BigDecimal currentValue = BigDecimal.ZERO;

    @Column
    private String unitOfMeasure;

    @Enumerated(EnumType.STRING)
    @Column
    private KrDirection direction = KrDirection.INCREASE;

    @Column
    private LocalDate startDate;

    @Column
    private LocalDate dueDate;

    @Column(precision = 5, scale = 2)
    private BigDecimal confidenceLevel;

    @Column(precision = 5, scale = 2)
    private BigDecimal progressPercentage = BigDecimal.ZERO;

    @Column(length = 2000)
    private String comments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private AppUser owner;

    @Column
    private Boolean verified = false;

    @Column
    private LocalDate verificationDate;
}

/**
 * Direção da meta do Key Result
 */
enum KrDirection {
    INCREASE,     // Aumentar (ex: aumentar em 20%)
    DECREASE,     // Diminuir (ex: reduzir em 15%)
    MAINTAIN,     // Manter (ex: manter acima de 95%)
    REACH         // Alcançar valor específico (ex: alcançar 1000)
}
