package com.pge.modules.riscos.dto;

import lombok.*;
import java.time.LocalDate;

/**
 * DTO para Risco Identificado
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RiscoIdentificadoDTO {
    
    private Long id;
    private String descricao;
    private String causa;
    private String efeito;
    private Long ataId;
    private String categoria;
    private Integer probabilidade;
    private Integer impacto;
    private Integer nivelRisco;
    private String status;
    private String responsavel;
    private String planoMitigacao;
    private LocalDate dataPrevistaMitigacao;
    private String medidasAdotadas;
    private LocalDate dataConclusao;
    private LocalDate dataCriacao;
    private LocalDate dataAtualizacao;
}
