package br.gov.pge.modulos.inovacao.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "premio_inovacao")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PremioInovacao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 200)
    private String nomeEdicao;
    
    @Column(length = 2000)
    private String regulamento;
    
    @Column
    private LocalDateTime dataInicioInscricoes;
    
    @Column
    private LocalDateTime dataFimInscricoes;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPremio status;
    
    @Column
    private Integer numeroInscricoes = 0;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projeto_vencedor_id")
    private ProjetoInovacao projetoVencedor;
    
    @Column(length = 2000)
    private String justificativaPremiacao;
    
    @Column(precision = 10, scale = 2)
    private Double valorPremio;
    
    @Column
    private LocalDateTime dataCerimonia;
    
    @Column
    private LocalDateTime dataCriacao = LocalDateTime.now();
    
    @Column
    private LocalDateTime dataAtualizacao;
}

enum StatusPremio {
    ABERTO,
    EM_AVALIACAO,
    ENCERRADO,
    PREMIADO
}
