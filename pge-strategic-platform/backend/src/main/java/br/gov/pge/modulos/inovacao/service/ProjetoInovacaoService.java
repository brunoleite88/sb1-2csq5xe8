package br.gov.pge.modulos.inovacao.service;

import br.gov.pge.modulos.inovacao.entity.*;
import br.gov.pge.modulos.inovacao.repository.ProjetoInovacaoRepository;
import br.gov.pge.core.exception.ResourceNotFoundException;
import br.gov.pge.core.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjetoInovacaoService {
    
    private final ProjetoInovacaoRepository repository;
    
    @Transactional(readOnly = true)
    public List<ProjetoInovacao> listarTodos() {
        return repository.findByAtivoTrue();
    }
    
    @Transactional(readOnly = true)
    public ProjetoInovacao buscarPorId(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Projeto de inovação não encontrado com ID: " + id));
    }
    
    @Transactional(readOnly = true)
    public List<ProjetoInovacao> listarPorEtapa(EtapaInovacao etapa) {
        return repository.findByEtapaAtual(etapa);
    }
    
    @Transactional(readOnly = true)
    public List<ProjetoInovacao> listarPorResponsavel(Long responsavelId) {
        return repository.findByResponsavelId(responsavelId);
    }
    
    @Transactional(readOnly = true)
    public List<Object[]> obterEstatisticasPorEtapa() {
        return repository.countPorEtapa();
    }
    
    @Transactional
    public ProjetoInovacao criar(ProjetoInovacao projeto) {
        if (projeto.getDataInicio() != null && projeto.getDataPrevisaoConclusao() != null) {
            if (projeto.getDataPrevisaoConclusao().isBefore(projeto.getDataInicio())) {
                throw new BusinessException("A data de previsão de conclusão deve ser posterior à data de início");
            }
        }
        projeto.setDataCriacao(LocalDateTime.now());
        return repository.save(projeto);
    }
    
    @Transactional
    public ProjetoInovacao atualizar(Long id, ProjetoInovacao projetoAtualizado) {
        ProjetoInovacao projeto = buscarPorId(id);
        
        projeto.setTitulo(projetoAtualizado.getTitulo());
        projeto.setDescricao(projetoAtualizado.getDescricao());
        projeto.setEtapaAtual(projetoAtualizado.getEtapaAtual());
        projeto.setResponsavel(projetoAtualizado.getResponsavel());
        projeto.setUnidadeOrganizacional(projetoAtualizado.getUnidadeOrganizacional());
        projeto.setTecnologiasUtilizadas(projetoAtualizado.getTecnologiasUtilizadas());
        projeto.setOrcamentoEstimado(projetoAtualizado.getOrcamentoEstimado());
        projeto.setDataInicio(projetoAtualizado.getDataInicio());
        projeto.setDataPrevisaoConclusao(projetoAtualizado.getDataPrevisaoConclusao());
        projeto.setDataEfetivacao(projetoAtualizado.getDataEfetivacao());
        projeto.setResultadosAlcancados(projetoAtualizado.getResultadosAlcancados());
        projeto.setIndicaPremioInovacao(projetoAtualizado.getIndicaPremioInovacao());
        projeto.setDataAtualizacao(LocalDateTime.now());
        
        // Validação de avanço de etapas
        if (projetoAtualizado.getEtapaAtual().getOrdem() < projeto.getEtapaAtual().getOrdem()) {
            throw new BusinessException("Não é possível retroceder etapas no fluxo de inovação");
        }
        
        return repository.save(projeto);
    }
    
    @Transactional
    public void avancarEtapa(Long id) {
        ProjetoInovacao projeto = buscarPorId(id);
        EtapaInovacao etapaAtual = projeto.getEtapaAtual();
        
        if (etapaAtual == EtapaInovacao.EFETIVACAO) {
            throw new BusinessException("Projeto já está na etapa final de efetivação");
        }
        
        EtapaInovacao proximaEtapa = EtapaInovacao.values()[etapaAtual.getOrdem()];
        projeto.setEtapaAtual(proximaEtapa);
        projeto.setDataAtualizacao(LocalDateTime.now());
        
        if (proximaEtapa == EtapaInovacao.EFETIVACAO) {
            projeto.setDataEfetivacao(LocalDateTime.now());
        }
        
        repository.save(projeto);
    }
    
    @Transactional
    public void excluirLogico(Long id) {
        ProjetoInovacao projeto = buscarPorId(id);
        projeto.setAtivo(false);
        projeto.setDataAtualizacao(LocalDateTime.now());
        repository.save(projeto);
    }
    
    @Transactional(readOnly = true)
    public List<ProjetoInovacao> listarIndicadosPremio() {
        return repository.findProjetosIndicadosPremio();
    }
}
