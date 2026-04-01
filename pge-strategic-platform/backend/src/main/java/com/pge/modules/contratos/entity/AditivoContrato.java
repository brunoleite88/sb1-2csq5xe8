package com.pge.modules.contratos.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.math.BigDecimal;

/**
 * Aditivo Contratual - Controle de prorrogações e alterações
 */
@Entity
@Table(name = "ctr_aditivo")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class AditivoContrato {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contrato_id", nullable = false)
    private Contrato contrato;
    
    @Column(nullable = false, length = 100)
    private String numeroAditivo;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoAditivo tipo;
    
    @Column(length = 1000)
    private String descricao;
    
    @Column
    private LocalDate dataAssinatura;
    
    @Column
    private LocalDate dataInicio;
    
    @Column
    private LocalDate dataFim;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal valorAditivo;
    
    @Column(length = 100)
    private String numeroProcesso;
    
    @Column(length = 2000)
    private String justificativa;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusAditivo status = StatusAditivo.EM_ANALISE;
    
    @Column(name = "data_criacao")
    private LocalDate dataCriacao;
    
    @Column(name = "data_atualizacao")
    private LocalDate dataAtualizacao;
    
    public enum TipoAditivo {
        PRORROGACAO_PRAZO, ALTERACAO_VALOR, ALTERACAO_ESPECIFICACAO, REEQUILIBRIO_ECONOMICO, OUTROS
    }
    
    public enum StatusAditivo {
        EM_ANALISE, APROVADO, PUBLICADO, CANCELADO
    }
}
