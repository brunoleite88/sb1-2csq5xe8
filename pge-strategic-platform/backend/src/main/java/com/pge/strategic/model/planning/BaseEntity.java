package com.pge.strategic.model.planning;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

/**
 * Entidade base para todas as entidades de planejamento
 */
@MappedSuperclass
@Data
public abstract class BaseEntity {

    @Column(nullable = false, updatable = false)
    protected LocalDate createdAt;

    @Column
    protected LocalDate updatedAt;

    @Column
    protected Boolean active = true;

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDate.now();
    }
}
