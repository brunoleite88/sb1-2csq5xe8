package com.pge.modules.contratos.dto;

import lombok.*;
import java.time.LocalDate;

/**
 * DTO para Contrato
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContratoDTO {
    
    private Long id;
    private String numero;
    private String objeto;
    private String fornecedor;
    private String cnpjFornecedor;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private Double valorTotal;
    private String status;
    private String responsavelFiscalizacao;
    private Boolean possuiAditivos;
    private Integer quantidadeAditivos;
    private LocalDate dataVencimentoAlerta;
    private String observacoes;
    private LocalDate dataCriacao;
    private LocalDate dataAtualizacao;
}
