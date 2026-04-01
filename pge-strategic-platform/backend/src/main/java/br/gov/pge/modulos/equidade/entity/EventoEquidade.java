package br.gov.pge.modulos.equidade.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "evento_equidade")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventoEquidade {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 200)
    private String nome;
    
    @Column(length = 2000)
    private String descricao;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoEvento tipo;
    
    @Column
    private LocalDateTime dataInicio;
    
    @Column
    private LocalDateTime dataFim;
    
    @Column(length = 500)
    private String local;
    
    @Column
    private Boolean online = false;
    
    @Column(length = 500)
    private String linkAcesso;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizador_id")
    private AppUser organizador;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grupo_trabalho_id")
    private GrupoTrabalhoEquidade grupoTrabalhoVinculado;
    
    @Column
    private Integer numeroParticipantesEstimado;
    
    @Column
    private Integer numeroParticipantesReal;
    
    @Column(length = 2000)
    private String resultadosAlcancados;
    
    @Column(length = 2000)
    private String materialDisponivel;
    
    @Column
    private LocalDateTime dataCriacao = LocalDateTime.now();
    
    @Column
    private LocalDateTime dataAtualizacao;
    
    @Column
    private Boolean ativo = true;
}

enum TipoEvento {
    PALESTRA("Palestra"),
    WORKSHOP("Workshop"),
    CURSO("Curso"),
    SEMINARIO("Seminário"),
    MESA_REDONDA("Mesa Redonda"),
    RODA_CONVERSA("Roda de Conversa"),
    CAMPANHA("Campanha"),
    CERIMONIA("Cerimônia"),
    OUTRO("Outro");
    
    private final String descricao;
    
    TipoEvento(String descricao) {
        this.descricao = descricao;
    }
    
    public String getDescricao() {
        return descricao;
    }
}
