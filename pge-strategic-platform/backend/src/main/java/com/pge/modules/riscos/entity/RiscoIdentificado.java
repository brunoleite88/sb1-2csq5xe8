package com.pge.modules.riscos.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

/**
 * Risco Identificado - Mapeamento de riscos institucionais
 */
@Entity
@Table(name = "risco_identificado")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class RiscoIdentificado {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    
    @Column(nullable = false, length = 300)
    private String descricao;
    
    @Column(length = 1000)
    private String causa;
    
    @Column(length = 1000)
    private String efeito;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ata_id")
    private AtaReuniaoRisco ata;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoriaRisco categoria;
    
    @Column(nullable = false)
    private Integer probabilidade; // 1-5
    
    @Column(nullable = false)
    private Integer impacto; // 1-5
    
    @Column(nullable = false)
    private Integer nivelRisco; // calculado: probabilidade x impacto
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusRisco status = StatusRisco.IDENTIFICADO;
    
    @Column(length = 200)
    private String responsavel;
    
    @Column(length = 1000)
    private String planoMitigacao;
    
    @Column
    private LocalDate dataPrevistaMitigacao;
    
    @Column(length = 1000)
    private String medidasAdotadas;
    
    @Column
    private LocalDate dataConclusao;
    
    @Column(name = "data_criacao")
    private LocalDate dataCriacao;
    
    @Column(name = "data_atualizacao")
    private LocalDate dataAtualizacao;
    
    public enum CategoriaRisco {
        ESTRATEGICO, OPERACIONAL, FINANCEIRO, COMPLIANCE, TECNOLOGICO, JURIDICO, REPUTACIONAL
    }
    
    public enum StatusRisco {
        IDENTIFICADO, ANALISADO, EM_TRATAMENTO, MITIGADO, MONITORADO, ENCERRADO
    }
    
    @PrePersist
    @PreUpdate
    private void calcularNivelRisco() {
        if (probabilidade != null && impacto != null) {
            this.nivelRisco = probabilidade * impacto;
        }
    }
}
