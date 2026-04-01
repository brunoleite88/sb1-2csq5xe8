package br.gov.pge.modulos.equidade.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "indicador_diversidade")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IndicadorDiversidade {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoIndicador tipo;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unidade_organizacional_id")
    private OrganizationalUnit unidadeOrganizacional;
    
    @Column
    private Integer totalServidores;
    
    @Column
    private Integer quantidadeGeneroFeminino;
    
    @Column
    private Integer quantidadeGeneroMasculino;
    
    @Column
    private Integer quantidadeGeneroNaoBinario;
    
    @Column
    private Integer quantidadeRacaBranca;
    
    @Column
    private Integer quantidadeRacaPreta;
    
    @Column
    private Integer quantidadeRacaParda;
    
    @Column
    private Integer quantidadeRacaAmarela;
    
    @Column
    private Integer quantidadeRacaIndigena;
    
    @Column
    private Integer quantidadeOrientacaoHeterossexual;
    
    @Column
    private Integer quantidadeOrientacaoHomossexual;
    
    @Column
    private Integer quantidadeOrientacaoBisexual;
    
    @Column
    private Integer quantidadeOrientacaoOutros;
    
    @Column
    private Integer quantidadeGeracaoAte25anos;
    
    @Column
    private Integer quantidadeGeracao26a35anos;
    
    @Column
    private Integer quantidadeGeracao36a45anos;
    
    @Column
    private Integer quantidadeGeracao46a55anos;
    
    @Column
    private Integer quantidadeGeracaoAcima55anos;
    
    @Column
    private Integer quantidadePCD;
    
    @Column(length = 2000)
    private String observacoes;
    
    @Column
    private LocalDateTime dataReferencia;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responsavel_preenchimento_id")
    private AppUser responsavelPreenchimento;
    
    @Column
    private LocalDateTime dataCriacao = LocalDateTime.now();
    
    @Column
    private LocalDateTime dataAtualizacao;
    
    @Column
    private Boolean ativo = true;
}

enum TipoIndicador {
    GERAL("Geral - Todas as métricas"),
    GENERO("Gênero"),
    RACA_ETNIA("Raça e Etnia"),
    ORIENTACAO_SEXUAL("Orientação Sexual"),
    GERACIONAL("Geracional"),
    PCD("Pessoa com Deficiência");
    
    private final String descricao;
    
    TipoIndicador(String descricao) {
        this.descricao = descricao;
    }
    
    public String getDescricao() {
        return descricao;
    }
}
