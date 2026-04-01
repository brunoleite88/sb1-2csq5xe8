package br.gov.pge.modulos.equidade.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "relatorio_conformidade_legal")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RelatorioConformidadeLegal {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 300)
    private String titulo;
    
    @Column(length = 2000)
    private String descricao;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoRelatorio tipo;
    
    @Column
    private LocalDateTime periodoInicio;
    
    @Column
    private LocalDateTime periodoFim;
    
    @Column(length = 5000)
    private String fundamentacaoLegal;
    
    @Column(length = 5000)
    private String analiseConformidade;
    
    @Column(length = 5000)
    private String recomendacoes;
    
    @Column(length = 5000)
    private String planoAcaoCorretivo;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "elaborador_id")
    private AppUser elaborador;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aprovador_id")
    private AppUser aprovador;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusRelatorio status;
    
    @Column
    private LocalDateTime dataSubmissao;
    
    @Column
    private LocalDateTime dataAprovacao;
    
    @Column(length = 500)
    private String caminhoArquivo;
    
    @Column
    private Boolean publicado = false;
    
    @Column
    private LocalDateTime dataCriacao = LocalDateTime.now();
    
    @Column
    private LocalDateTime dataAtualizacao;
}

enum TipoRelatorio {
    DIVERSIDADE_GENERO("Diversidade de Gênero"),
    DIVERSIDADE_RACA("Diversidade Racial e Étnica"),
    INCLUSAO_PCD("Inclusão de Pessoas com Deficiência"),
    EQUIDADE_SALARIAL("Equidade Salarial"),
    ASS EDIO_MORAL("Prevenção ao Assédio Moral"),
    DISCRIMINACAO_LGBTQIAPN("Não Discriminação LGBTQIAPN+"),
    ACESSIBILIDADE("Acessibilidade"),
    COTAS_CONCURSOS("Cumprimento de Cotas em Concursos"),
    OUTRO("Outro");
    
    private final String descricao;
    
    TipoRelatorio(String descricao) {
        this.descricao = descricao;
    }
    
    public String getDescricao() {
        return descricao;
    }
}

enum StatusRelatorio {
    EM_ELABORACAO,
    SUBMETIDO,
    EM_ANALISE,
    APROVADO,
    PUBLICADO,
    ARQUIVADO
}
