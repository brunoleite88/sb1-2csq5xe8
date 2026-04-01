package com.pge.strategic.model;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entidade de Usuário do Sistema
 * 
 * Representa os usuários com diferentes níveis hierárquicos
 * de acesso ao sistema.
 */
@Entity
@Table(name = "app_user",
       indexes = {
           @Index(name = "idx_user_unit", columnList = "organ_unit_id"),
           @Index(name = "idx_user_email", columnList = "email"),
           @Index(name = "idx_user_role", columnList = "role_level")
       },
       uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * E-mail do usuário (login)
     */
    @Column(name = "email", nullable = false, length = 255)
    private String email;

    /**
     * Hash da senha
     */
    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    /**
     * Nome completo
     */
    @Column(name = "full_name", nullable = false, length = 255)
    private String fullName;

    /**
     * Número de matrícula ou OAB
     */
    @Column(name = "registration_number", length = 50)
    private String registrationNumber;

    /**
     * Unidade organizacional vinculada
     */
    @Column(name = "organ_unit_id")
    private Long organUnitId;

    /**
     * Nível hierárquico do usuário
     * 1-ADMIN, 2-PROCURADOR_GERAL, 3-REGIONAL, 4-PROCURADOR, 5-ANALISTA, 6-VISUALIZADOR
     */
    @Column(name = "role_level", nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private RoleLevel roleLevel = RoleLevel.VISUALIZADOR;

    /**
     * Status do usuário
     */
    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;

    /**
     * Data do último login
     */
    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    /**
     * Data de criação
     */
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    /**
     * Data de atualização
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    /**
     * Níveis hierárquicos de acesso
     */
    public enum RoleLevel {
        ADMIN(1, "Administrador Global", "Acesso total ao sistema e customizações"),
        PROCURADOR_GERAL(2, "Procurador Geral", "Visão institucional completa"),
        REGIONAL(3, "Procurador Regional/Setorial", "Gestão de sua unidade"),
        PROCURADOR(4, "Procurador", "Atuação em processos e consultas"),
        ANALISTA(5, "Analista Técnico", "Suporte e elaboração de minutas"),
        VISUALIZADOR(6, "Visualizador", "Consulta limitada a informações públicas");

        private final int level;
        private final String title;
        private final String description;

        RoleLevel(int level, String title, String description) {
            this.level = level;
            this.title = title;
            this.description = description;
        }

        public int getLevel() {
            return level;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }
    }
}
