package com.pge.modules.contratos.service;

import com.pge.modules.contratos.dto.ContratoDTO;
import com.pge.modules.contratos.entity.Contrato;
import com.pge.modules.contratos.repository.ContratoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service para Gestão de Contratos
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ContratoService {
    
    private final ContratoRepository repository;
    
    public List<ContratoDTO> listarTodos() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    public ContratoDTO buscarPorId(Long id) {
        return repository.findById(id)
                .map(this::toDTO)
                .orElse(null);
    }
    
    public List<ContratoDTO> listarPorStatus(String status) {
        return repository.findByStatus(status).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    public List<ContratoDTO> listarVencendoProximamente(int dias) {
        LocalDate dataLimite = LocalDate.now().plusDays(dias);
        return repository.findByDataFimBefore(dataLimite).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    public ContratoDTO salvar(ContratoDTO dto) {
        Contrato contrato = toEntity(dto);
        
        if (dto.getId() == null) {
            contrato.setDataCriacao(LocalDate.now());
        }
        contrato.setDataAtualizacao(LocalDate.now());
        
        // Calcular alerta de vencimento (90 dias antes)
        if (contrato.getDataFim() != null) {
            contrato.setDataVencimentoAlerta(contrato.getDataFim().minusDays(90));
        }
        
        Contrato salvo = repository.save(contrato);
        return toDTO(salvo);
    }
    
    public void deletar(Long id) {
        repository.deleteById(id);
    }
    
    private ContratoDTO toDTO(Contrato entity) {
        return ContratoDTO.builder()
                .id(entity.getId())
                .numero(entity.getNumero())
                .objeto(entity.getObjeto())
                .fornecedor(entity.getFornecedor())
                .cnpjFornecedor(entity.getCnpjFornecedor())
                .dataInicio(entity.getDataInicio())
                .dataFim(entity.getDataFim())
                .valorTotal(entity.getValorTotal())
                .status(entity.getStatus())
                .responsavelFiscalizacao(entity.getResponsavelFiscalizacao())
                .possuiAditivos(entity.getPossuiAditivos())
                .quantidadeAditivos(entity.getQuantidadeAditivos())
                .dataVencimentoAlerta(entity.getDataVencimentoAlerta())
                .observacoes(entity.getObservacoes())
                .dataCriacao(entity.getDataCriacao())
                .dataAtualizacao(entity.getDataAtualizacao())
                .build();
    }
    
    private Contrato toEntity(ContratoDTO dto) {
        return Contrato.builder()
                .id(dto.getId())
                .numero(dto.getNumero())
                .objeto(dto.getObjeto())
                .fornecedor(dto.getFornecedor())
                .cnpjFornecedor(dto.getCnpjFornecedor())
                .dataInicio(dto.getDataInicio())
                .dataFim(dto.getDataFim())
                .valorTotal(dto.getValorTotal())
                .status(dto.getStatus())
                .responsavelFiscalizacao(dto.getResponsavelFiscalizacao())
                .possuiAditivos(dto.getPossuiAditivos())
                .quantidadeAditivos(dto.getQuantidadeAditivos())
                .dataVencimentoAlerta(dto.getDataVencimentoAlerta())
                .observacoes(dto.getObservacoes())
                .dataCriacao(dto.getDataCriacao())
                .dataAtualizacao(dto.getDataAtualizacao())
                .build();
    }
}
