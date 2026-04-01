package br.gov.pge.modulos.inovacao.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "projeto_inovacao")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjetoInovacao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 200)
    private String titulo;
    
    @Column(length = 2000)
    private String descricao;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EtapaInovacao etapaAtual;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responsavel_id")
    private AppUser responsavel;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unidade_organizacional_id")
    private OrganizationalUnit unidadeOrganizacional;
    
    @Column(length = 500)
    private String tecnologiasUtilizadas;
    
    @Column(precision = 10, scale = 2)
    private Double orcamentoEstimado;
    
    @Column
    private LocalDateTime dataInicio;
    
    @Column
    private LocalDateTime dataPrevisaoConclusao;
    
    @Column
    private LocalDateTime dataEfetivacao;
    
    @Column(length = 2000)
    private String resultadosAlcancados;
    
    @Column
    private Boolean indicaPremioInovacao = false;
    
    @Column
    private LocalDateTime dataCriacao = LocalDateTime.now();
    
    @Column
    private LocalDateTime dataAtualizacao;
    
    @Column
    private Boolean ativo = true;
}
