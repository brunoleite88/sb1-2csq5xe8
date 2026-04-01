package com.pge.modules.riscos.service;

import com.pge.modules.riscos.dto.RiscoIdentificadoDTO;
import com.pge.modules.riscos.entity.RiscoIdentificado;
import com.pge.modules.riscos.repository.RiscoIdentificadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service para Gestão de Riscos Identificados
 */
@Service
@RequiredArgsConstructor
@Transactional
public class RiscoIdentificadoService {
    
    private final RiscoIdentificadoRepository repository;
    
    public List<RiscoIdentificadoDTO> listarTodos() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    public RiscoIdentificadoDTO buscarPorId(Long id) {
        return repository.findById(id)
                .map(this::toDTO)
                .orElse(null);
    }
    
    public List<RiscoIdentificadoDTO> listarPorAta(Long ataId) {
        return repository.findByAtaId(ataId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    public List<RiscoIdentificadoDTO> listarPorStatus(String status) {
        return repository.findByStatus(RiscoIdentificado.StatusRisco.valueOf(status)).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    public RiscoIdentificadoDTO salvar(RiscoIdentificadoDTO dto) {
        RiscoIdentificado risco = toEntity(dto);
        
        if (dto.getId() == null) {
            risco.setDataCriacao(LocalDate.now());
        }
        risco.setDataAtualizacao(LocalDate.now());
        
        RiscoIdentificado salvo = repository.save(risco);
        return toDTO(salvo);
    }
    
    public void deletar(Long id) {
        repository.deleteById(id);
    }
    
    private RiscoIdentificadoDTO toDTO(RiscoIdentificado entity) {
        return RiscoIdentificadoDTO.builder()
                .id(entity.getId())
                .descricao(entity.getDescricao())
                .causa(entity.getCausa())
                .efeito(entity.getEfeito())
                .ataId(entity.getAta() != null ? entity.getAta().getId() : null)
                .categoria(entity.getCategoria().name())
                .probabilidade(entity.getProbabilidade())
                .impacto(entity.getImpacto())
                .nivelRisco(entity.getNivelRisco())
                .status(entity.getStatus().name())
                .responsavel(entity.getResponsavel())
                .planoMitigacao(entity.getPlanoMitigacao())
                .dataPrevistaMitigacao(entity.getDataPrevistaMitigacao())
                .medidasAdotadas(entity.getMedidasAdotadas())
                .dataConclusao(entity.getDataConclusao())
                .dataCriacao(entity.getDataCriacao())
                .dataAtualizacao(entity.getDataAtualizacao())
                .build();
    }
    
    private RiscoIdentificado toEntity(RiscoIdentificadoDTO dto) {
        RiscoIdentificado.RiscoIdentificadoBuilder builder = RiscoIdentificado.builder()
                .id(dto.getId())
                .descricao(dto.getDescricao())
                .causa(dto.getCausa())
                .efeito(dto.getEfeito())
                .categoria(RiscoIdentificado.CategoriaRisco.valueOf(dto.getCategoria()))
                .probabilidade(dto.getProbabilidade())
                .impacto(dto.getImpacto())
                .nivelRisco(dto.getNivelRisco())
                .status(RiscoIdentificado.StatusRisco.valueOf(dto.getStatus()))
                .responsavel(dto.getResponsavel())
                .planoMitigacao(dto.getPlanoMitigacao())
                .dataPrevistaMitigacao(dto.getDataPrevistaMitigacao())
                .medidasAdotadas(dto.getMedidasAdotadas())
                .dataConclusao(dto.getDataConclusao())
                .dataCriacao(dto.getDataCriacao())
                .dataAtualizacao(dto.getDataAtualizacao());
        
        // Ata seria carregada separadamente se necessário
        return builder.build();
    }
}
