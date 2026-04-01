package com.pge.strategic.model.planning;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.ArrayList;
import java.util.List;

/**
 * Elemento do Mapa Estratégico
 * Representa visualmente os objetivos no mapa estratégico
 */
@Entity
@Table(name = "strategic_map_element")
@Data
@EqualsAndHashCode(callSuper = true)
public class StrategicMapElement extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "strategic_plan_id", nullable = false)
    private StrategicPlan strategicPlan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "strategic_objective_id", nullable = false)
    private StrategicObjective strategicObjective;

    @Column(nullable = false)
    private Integer positionX;

    @Column(nullable = false)
    private Integer positionY;

    @Column
    private Integer width = 200;

    @Column
    private Integer height = 150;

    @Enumerated(EnumType.STRING)
    @Column
    private MapElementType type = MapElementType.OBJECTIVE;

    @Column(length = 500)
    private String color;

    @Column
    private Integer zIndex = 1;

    @Column
    private Boolean visible = true;

    @Column
    private Boolean locked = false;

    @OneToMany(mappedBy = "strategicMapElement", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MapConnection> connections = new ArrayList<>();

    @Column(length = 2000)
    private String notes;
}

/**
 * Tipos de Elementos do Mapa
 */
enum MapElementType {
    OBJECTIVE,          // Objetivo
    PERSPECTIVE,        // Perspectiva BSC
    AXIS,               // Eixo Estratégico
    THEME,              // Tema
    GROUP               // Grupo/Agrupamento
}

/**
 * Conexão entre elementos do mapa
 */
@Entity
@Table(name = "map_connection")
@Data
public class MapConnection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "strategic_map_element_id", nullable = false)
    private StrategicMapElement strategicMapElement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_element_id", nullable = false)
    private StrategicMapElement targetElement;

    @Enumerated(EnumType.STRING)
    @Column
    private ConnectionType connectionType = ConnectionType.CAUSE_EFFECT;

    @Column(length = 500)
    private String label;

    @Column
    private String color;

    @Column
    private Integer strokeWidth = 2;

    @Column
    private Boolean dashed = false;

    @Column
    private String arrowStyle = "classic";
}

/**
 * Tipos de Conexão
 */
enum ConnectionType {
    CAUSE_EFFECT,       // Causa-Efeito
    SUPPORT,            // Suporte
    INFLUENCE,          // Influência
    DEPENDENCY,         // Dependência
    FLOW                // Fluxo
}
