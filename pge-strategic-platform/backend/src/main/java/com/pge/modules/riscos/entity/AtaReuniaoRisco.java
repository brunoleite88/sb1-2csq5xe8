package com.pge.modules.riscos.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Registro de Ata do Comitê de Riscos Institucionais
 */
@Entity
@Table(name = "risco_ata_reuniao")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class AtaReuniaoRisco {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    
    @Column(nullable = false)
    private Integer numeroAta;
    
    @Column(nullable = false)
    private Integer ano;
    
    @Column(nullable = false)
    private LocalDate dataReuniao;
    
    @Column(length = 500)
    private String pauta;
    
    @Column(length = 2000)
    private String deliberacoes;
    
    @Column(length = 2000)
    private String encaminhamentos;
    
    @Column(length = 200)
    private String secretario;
    
    @Column(length = 200)
    private String presidente;
    
    @Column(length = 500)
    private String localReuniao;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusAta status = StatusAta.RASCUNHO;
    
    @Column(length = 1000)
    private String arquivoAta;
    
    @OneToMany(mappedBy = "ata", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<RiscoIdentificado> riscosApresentados = new HashSet<>();
    
    @Column(name = "data_criacao")
    private LocalDate dataCriacao;
    
    @Column(name = "data_atualizacao")
    private LocalDate dataAtualizacao;
    
    public enum StatusAta {
        RASCUNHO, APROVADA, PUBLICADA
    }
}
