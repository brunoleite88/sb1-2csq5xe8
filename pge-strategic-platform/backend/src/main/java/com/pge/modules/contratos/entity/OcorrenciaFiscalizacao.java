package com.pge.modules.contratos.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

/**
 * Ocorrência de Fiscalização - Registro de medições, atestes e ocorrências
 */
@Entity
@Table(name = "ctr_ocorrencia_fiscalizacao")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class OcorrenciaFiscalizacao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contrato_id", nullable = false)
    private Contrato contrato;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoOcorrencia tipo;
    
    @Column(nullable = false, length = 1000)
    private String descricao;
    
    @Column(nullable = false)
    private LocalDate dataOcorrencia;
    
    @Column(length = 200)
    private String fiscalResponsavel;
    
    @Column(length = 50)
    private String numeroDocumento;
    
    @Column(precision = 10, scale = 2)
    private Double medicaoQuantidade;
    
    @Column(length = 50)
    private String unidadeMedida;
    
    @Column(length = 1000)
    private String parecerFiscal;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusOcorrencia status = StatusOcorrencia.REGISTRADA;
    
    @Column(length = 200)
    private String fornecedorNotificado;
    
    @Column
    private LocalDate dataRespostaFornecedor;
    
    @Column(length = 1000)
    private String respostaFornecedor;
    
    @Column(length = 1000)
    private String medidasAplicadas;
    
    @Column(name = "data_criacao")
    private LocalDate dataCriacao;
    
    @Column(name = "data_atualizacao")
    private LocalDate dataAtualizacao;
    
    public enum TipoOcorrencia {
        MEDICAO, ATESTE, IRREGULARIDADE, SUSPENSAO, MULTA, ADVERTENCIA, ELOGIO, OUTROS
    }
    
    public enum StatusOcorrencia {
        REGISTRADA, EM_ANALISE, RESPONDIDA, SOLUCIONADA, ENCAMINHADA, ARQUIVADA
    }
}
