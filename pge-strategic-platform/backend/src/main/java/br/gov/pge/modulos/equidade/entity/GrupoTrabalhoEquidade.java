package br.gov.pge.modulos.equidade.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "grupo_trabalho_equidade")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GrupoTrabalhoEquidade {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 200)
    private String nome;
    
    @Column(length = 2000)
    private String objetivo;
    
    @Column(length = 2000)
    private String escopoAtuacao;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoGrupo tipo;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coordenador_id")
    private AppUser coordenador;
    
    @ManyToMany
    @JoinTable(
        name = "grupo_trabalho_membros",
        joinColumns = @JoinColumn(name = "grupo_id"),
        inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private Set<AppUser> membros = new HashSet<>();
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unidade_vinculada_id")
    private OrganizationalUnit unidadeVinculada;
    
    @Column
    private LocalDateTime dataCriacao = LocalDateTime.now();
    
    @Column
    private LocalDateTime dataAtualizacao;
    
    @Column
    private Boolean ativo = true;
}

enum TipoGrupo {
    GENERO("Gênero e Equidade de Gênero"),
    RACA_ETNIA("Raça e Etnia"),
    LGBTQIAPN("LGBTQIAPN+"),
    PCD("Pessoa com Deficiência"),
    GERACIONAL("Diversidade Geracional"),
    INTERSECCIONAL("Interseccional"),
    OUTRO("Outro");
    
    private final String descricao;
    
    TipoGrupo(String descricao) {
        this.descricao = descricao;
    }
    
    public String getDescricao() {
        return descricao;
    }
}
