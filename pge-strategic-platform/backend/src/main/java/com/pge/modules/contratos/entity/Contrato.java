package com.pge.modules.contratos.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Contrato Ativo - Inventário e gestão de contratos
 */
@Entity
@Table(name = "ctr_contrato")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Contrato {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String numeroContrato;
    
    @Column(nullable = false)
    private Integer ano;
    
    @Column(nullable = false, length = 300)
    private String objeto;
    
    @Column(length = 200, nullable = false)
    private String fornecedor;
    
    @Column(length = 50)
    private String cnpjFornecedor;
    
    @Column(nullable = false)
    private BigDecimal valorTotal;
    
    @Column(nullable = false)
    private LocalDate dataInicio;
    
    @Column(nullable = false)
    private LocalDate dataFim;
    
    @Column
    private LocalDate dataProrrogacao;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusContrato status = StatusContrato.ATIVO;
    
    @Column(length = 200)
    private String gestorContrato;
    
    @Column(length = 200)
    private String fiscalContrato;
    
    @Column(length = 100)
    private String numeroProcesso;
    
    @Column(length = 100)
    private String numeroLicitation;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal valorEmpenhado = BigDecimal.ZERO;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal valorLiquidado = BigDecimal.ZERO;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal valorPago = BigDecimal.ZERO;
    
    @Column
    private Boolean alertaVencimento90 = false;
    
    @Column
    private Boolean alertaVencimento30 = false;
    
    @Column(length = 1000)
    private String observacoes;
    
    @OneToMany(mappedBy = "contrato", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<AditivoContrato> aditivos = new HashSet<>();
    
    @OneToMany(mappedBy = "contrato", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<OcorrenciaFiscalizacao> ocorrencias = new HashSet<>();
    
    @Column(name = "data_criacao")
    private LocalDate dataCriacao;
    
    @Column(name = "data_atualizacao")
    private LocalDate dataAtualizacao;
    
    public enum StatusContrato {
        ATIVO, SUSPENSO, ENCERRADO, VENCIDO, EM_PRORROGACAO, RESCINDIDO
    }
}
