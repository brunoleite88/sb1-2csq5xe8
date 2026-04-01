package com.pge.modules.capacitacao.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Evento de Capacitação - Gestão completa de eventos da ESAP
 */
@Entity
@Table(name = "cap_evento_capacitacao")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class EventoCapacitacao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    
    @Column(nullable = false, length = 200)
    private String titulo;
    
    @Column(length = 1000)
    private String descricao;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoEvento tipo;
    
    @Column(nullable = false)
    private LocalDate dataInicio;
    
    @Column(nullable = false)
    private LocalDate dataFim;
    
    @Column(nullable = false)
    private Integer cargaHoraria;
    
    @Column(length = 500)
    private String local;
    
    @Column(length = 200)
    private String instrutor;
    
    @Column(nullable = false)
    private Integer vagasTotais;
    
    @Column(nullable = false)
    private Integer vagasOcupadas = 0;
    
    @Column(nullable = false)
    private Boolean permiteListaEspera = true;
    
    @Column(nullable = false)
    private Boolean emiteCertificado = true;
    
    @Column(length = 500)
    private String urlInscricao;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusEvento status = StatusEvento.ABERTO;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "convenio_id")
    private ConvenioCapacitacao convenio;
    
    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<InscricaoEvento> inscricoes = new HashSet<>();
    
    @Column(name = "data_criacao")
    private LocalDate dataCriacao;
    
    @Column(name = "data_atualizacao")
    private LocalDate dataAtualizacao;
    
    public enum TipoEvento {
        CURSO, WORKSHOP, SEMINARIO, PALESTRA, CONFERENCIA, RESIDENCIA_JURIDICA, POS_GRADUACAO, MESTRADO, DOUTORADO
    }
    
    public enum StatusEvento {
        ABERTO, EM_ANDAMENTO, CONCLUIDO, CANCELADO, AGENDADO
    }
}
