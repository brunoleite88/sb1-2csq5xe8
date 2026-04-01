package com.pge.modules.capacitacao.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.math.BigDecimal;

/**
 * Inscrição em Evento de Capacitação - Controle de inscrições e presenças
 */
@Entity
@Table(name = "cap_inscricao_evento", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"evento_id", "servidor_id"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class InscricaoEvento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evento_id", nullable = false)
    private EventoCapacitacao evento;
    
    @Column(name = "servidor_id", nullable = false)
    private Long servidorId;
    
    @Column(length = 200, nullable = false)
    private String nomeServidor;
    
    @Column(length = 100)
    private String lotacao;
    
    @Column(length = 50)
    private String cargo;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusInscricao status = StatusInscricao.INSCRITO;
    
    @Column(nullable = false)
    private Boolean confirmouPresenca = false;
    
    @Column(nullable = false)
    private Boolean compareceu = false;
    
    @Column(nullable = false)
    private Boolean emitiuCertificado = false;
    
    @Column(length = 50)
    private String codigoCertificado;
    
    @Column(precision = 5, scale = 2)
    private BigDecimal notaAvaliacao;
    
    @Column(length = 1000)
    private String observacoes;
    
    @Column(name = "data_inscricao")
    private LocalDate dataInscricao;
    
    @Column(name = "data_confirmacao")
    private LocalDate dataConfirmacao;
    
    @Column(name = "data_conclusao")
    private LocalDate dataConclusao;
    
    @Column(name = "data_criacao")
    private LocalDate dataCriacao;
    
    @Column(name = "data_atualizacao")
    private LocalDate dataAtualizacao;
    
    public enum StatusInscricao {
        INSCRITO, CONFIRMADO, LISTA_ESPERA, CANCELADO, CONCLUIDO, REPROVADO
    }
}
