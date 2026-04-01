package com.pge.modules.memoria.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

/**
 * Súmula Administrativa - Enunciados editados e revisados
 */
@Entity
@Table(name = "mem_sumula_administrativa")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class SumulaAdministrativa {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    
    @Column(nullable = false, length = 20)
    private String numeroSumula;
    
    @Column(nullable = false, length = 1000)
    private String enunciado;
    
    @Column(length = 2000)
    private String fundamentacao;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusSumula status = StatusSumula.VIGENTE;
    
    @Column(nullable = false)
    private LocalDate dataEdicao;
    
    @Column
    private LocalDate dataRevisao;
    
    @Column(length = 200)
    private String relator;
    
    @Column(length = 100)
    private String numeroProcessoOrigem;
    
    @Column(length = 500)
    private String assunto;
    
    @Column(length = 500)
    private String arquivoPdf;
    
    @Column
    private Boolean publicadoDOE = false;
    
    @Column
    private LocalDate dataPublicacao;
    
    @Column(length = 1000)
    private String observacoes;
    
    @Column(name = "data_criacao")
    private LocalDate dataCriacao;
    
    @Column(name = "data_atualizacao")
    private LocalDate dataAtualizacao;
    
    public enum StatusSumula {
        VIGENTE, REVOGADA, SUSPENSA, EM_REVISAO
    }
}
