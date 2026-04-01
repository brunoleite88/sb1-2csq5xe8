package com.pge.modules.capacitacao.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Convênio de Capacitação - Gestão de parcerias acadêmicas
 */
@Entity
@Table(name = "cap_convenio_capacitacao")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class ConvenioCapacitacao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    
    @Column(nullable = false, length = 200)
    private String instituicao;
    
    @Column(length = 1000)
    private String objeto;
    
    @Column(nullable = false)
    private LocalDate dataInicio;
    
    @Column(nullable = false)
    private LocalDate dataFim;
    
    @Column(length = 100)
    private String numeroProcesso;
    
    @Column(length = 50)
    private String valorTotal;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusConvenio status = StatusConvenio.ATIVO;
    
    @Column(length = 500)
    private String coordenador;
    
    @Column(length = 200)
    private String emailContato;
    
    @Column(length = 50)
    private String telefoneContato;
    
    @Column(length = 1000)
    private String observacoes;
    
    @OneToMany(mappedBy = "convenio", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<EventoCapacitacao> eventos = new HashSet<>();
    
    @Column(name = "data_criacao")
    private LocalDate dataCriacao;
    
    @Column(name = "data_atualizacao")
    private LocalDate dataAtualizacao;
    
    public enum StatusConvenio {
        ATIVO, SUSPENSO, ENCERRADO, EM_NEGOCIACAO, VENCIDO
    }
}
