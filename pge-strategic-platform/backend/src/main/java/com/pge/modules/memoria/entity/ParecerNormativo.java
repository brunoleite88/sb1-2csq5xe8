package com.pge.modules.memoria.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Parecer Normativo - Banco de pareceres do Conselho Superior
 */
@Entity
@Table(name = "mem_parecer_normativo")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class ParecerNormativo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    
    @Column(nullable = false, length = 50)
    private String numeroParecer;
    
    @Column(nullable = false)
    private Integer ano;
    
    @Column(nullable = false, length = 500)
    private String ementa;
    
    @Column(length = 5000)
    private String textoIntegral;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoParecer tipo;
    
    @Column(length = 1000)
    private String assunto;
    
    @Column(length = 200)
    private String relator;
    
    @Column(nullable = false)
    private LocalDate dataAprovacao;
    
    @Column(length = 100)
    private String numeroProcesso;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusParecer status = StatusParecer.VIGENTE;
    
    @Column(length = 500)
    private String arquivoPdf;
    
    @Column
    private Boolean publicadoDOE = false;
    
    @Column
    private LocalDate dataPublicacao;
    
    @OneToMany(mappedBy = "parecer", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<PalavraChaveParecer> palavrasChave = new HashSet<>();
    
    @Column(name = "data_criacao")
    private LocalDate dataCriacao;
    
    @Column(name = "data_atualizacao")
    private LocalDate dataAtualizacao;
    
    public enum TipoParecer {
        NORMATIVO, CONSULTIVO, REVISAO, COMPLEMENTAR
    }
    
    public enum StatusParecer {
        VIGENTE, REVOGADO, SUSPENSO, EM_REVISAO
    }
}
