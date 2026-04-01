package br.gov.pge.modulos.inovacao.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tecnologia_implementada")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TecnologiaImplementada {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 200)
    private String nomeTecnologia;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoTecnologia tipo;
    
    @Column(length = 2000)
    private String descricao;
    
    @Column(length = 500)
    private String fornecedor;
    
    @Column
    private LocalDateTime dataImplementacao;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unidade_responsavel_id")
    private OrganizationalUnit unidadeResponsavel;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projeto_origem_id")
    private ProjetoInovacao projetoOrigem;
    
    @Column
    private Integer numeroUsuariosImpactados;
    
    @Column(precision = 10, scale = 2)
    private Double economiaEstimada;
    
    @Column(length = 2000)
    private String beneficiosAlcancados;
    
    @Column
    private Boolean documentacaoDisponivel = true;
    
    @Column(length = 500)
    private String linkDocumentacao;
    
    @Column
    private LocalDateTime dataCriacao = LocalDateTime.now();
    
    @Column
    private LocalDateTime dataAtualizacao;
    
    @Column
    private Boolean ativo = true;
}

enum TipoTecnologia {
    INTELIGENCIA_ARTIFICIAL("Inteligência Artificial"),
    RPA("Automação Robótica de Processos - RPA"),
    MACHINE_LEARNING("Machine Learning"),
    PROCESSAMENTO_LINGUAGEM_NATURAL("Processamento de Linguagem Natural"),
    ANALYTICS("Analytics e Business Intelligence"),
    BLOCKCHAIN("Blockchain"),
    CLOUD_COMPUTING("Cloud Computing"),
    API("APIs e Integrações"),
    OUTROS("Outros");
    
    private final String descricao;
    
    TipoTecnologia(String descricao) {
        this.descricao = descricao;
    }
    
    public String getDescricao() {
        return descricao;
    }
}
