package br.gov.pge.modulos.equidade.service;

import br.gov.pge.modulos.equidade.entity.*;
import br.gov.pge.modulos.equidade.repository.IndicadorDiversidadeRepository;
import br.gov.pge.core.exception.ResourceNotFoundException;
import br.gov.pge.core.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class IndicadorDiversidadeService {
    
    private final IndicadorDiversidadeRepository repository;
    
    @Transactional(readOnly = true)
    public List<IndicadorDiversidade> listarTodos() {
        return repository.findByAtivoTrue();
    }
    
    @Transactional(readOnly = true)
    public IndicadorDiversidade buscarPorId(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Indicador de diversidade não encontrado com ID: " + id));
    }
    
    @Transactional(readOnly = true)
    public List<IndicadorDiversidade> listarPorUnidade(Long unidadeId) {
        return repository.findByUnidadeOrganizacionalId(unidadeId);
    }
    
    @Transactional(readOnly = true)
    public List<IndicadorDiversidade> listarPorTipo(TipoIndicador tipo) {
        return repository.findByTipo(tipo);
    }
    
    @Transactional(readOnly = true)
    public List<IndicadorDiversidade> listarPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return repository.findByDataReferenciaBetween(inicio, fim);
    }
    
    @Transactional(readOnly = true)
    public List<Object[]> calcularMediasPorTipo() {
        return repository.calcularMediasPorTipo();
    }
    
    @Transactional
    public IndicadorDiversidade criar(IndicadorDiversidade indicador) {
        validarDados(indicador);
        indicador.setDataCriacao(LocalDateTime.now());
        return repository.save(indicador);
    }
    
    @Transactional
    public IndicadorDiversidade atualizar(Long id, IndicadorDiversidade indicadorAtualizado) {
        IndicadorDiversidade indicador = buscarPorId(id);
        validarDados(indicadorAtualizado);
        
        indicador.setTipo(indicadorAtualizado.getTipo());
        indicador.setUnidadeOrganizacional(indicadorAtualizado.getUnidadeOrganizacional());
        indicador.setTotalServidores(indicadorAtualizado.getTotalServidores());
        indicador.setQuantidadeGeneroFeminino(indicadorAtualizado.getQuantidadeGeneroFeminino());
        indicador.setQuantidadeGeneroMasculino(indicadorAtualizado.getQuantidadeGeneroMasculino());
        indicador.setQuantidadeGeneroNaoBinario(indicadorAtualizado.getQuantidadeGeneroNaoBinario());
        indicador.setQuantidadeRacaBranca(indicadorAtualizado.getQuantidadeRacaBranca());
        indicador.setQuantidadeRacaPreta(indicadorAtualizado.getQuantidadeRacaPreta());
        indicador.setQuantidadeRacaParda(indicadorAtualizado.getQuantidadeRacaParda());
        indicador.setQuantidadeRacaAmarela(indicadorAtualizado.getQuantidadeRacaAmarela());
        indicador.setQuantidadeRacaIndigena(indicadorAtualizado.getQuantidadeRacaIndigena());
        indicador.setQuantidadeOrientacaoHeterossexual(indicadorAtualizado.getQuantidadeOrientacaoHeterossexual());
        indicador.setQuantidadeOrientacaoHomossexual(indicadorAtualizado.getQuantidadeOrientacaoHomossexual());
        indicador.setQuantidadeOrientacaoBisexual(indicadorAtualizado.getQuantidadeOrientacaoBisexual());
        indicador.setQuantidadeOrientacaoOutros(indicadorAtualizado.getQuantidadeOrientacaoOutros());
        indicador.setQuantidadeGeracaoAte25anos(indicadorAtualizado.getQuantidadeGeracaoAte25anos());
        indicador.setQuantidadeGeracao26a35anos(indicadorAtualizado.getQuantidadeGeracao26a35anos());
        indicador.setQuantidadeGeracao36a45anos(indicadorAtualizado.getQuantidadeGeracao36a45anos());
        indicador.setQuantidadeGeracao46a55anos(indicadorAtualizado.getQuantidadeGeracao46a55anos());
        indicador.setQuantidadeGeracaoAcima55anos(indicadorAtualizado.getQuantidadeGeracaoAcima55anos());
        indicador.setQuantidadePCD(indicadorAtualizado.getQuantidadePCD());
        indicador.setObservacoes(indicadorAtualizado.getObservacoes());
        indicador.setDataReferencia(indicadorAtualizado.getDataReferencia());
        indicador.setResponsavelPreenchimento(indicadorAtualizado.getResponsavelPreenchimento());
        indicador.setDataAtualizacao(LocalDateTime.now());
        
        return repository.save(indicador);
    }
    
    @Transactional
    public void excluirLogico(Long id) {
        IndicadorDiversidade indicador = buscarPorId(id);
        indicador.setAtivo(false);
        indicador.setDataAtualizacao(LocalDateTime.now());
        repository.save(indicador);
    }
    
    @Transactional(readOnly = true)
    public Map<String, Object> gerarRelatorioConsolidado(Long unidadeId, LocalDateTime periodoInicio, LocalDateTime periodoFim) {
        List<IndicadorDiversidade> indicadores = listarPorPeriodo(periodoInicio, periodoFim);
        
        Map<String, Object> relatorio = new HashMap<>();
        relatorio.put("periodoInicio", periodoInicio);
        relatorio.put("periodoFim", periodoFim);
        relatorio.put("totalRegistros", indicadores.size());
        
        // Cálculos consolidados
        int totalServidores = 0;
        int totalGeneroFeminino = 0;
        int totalGeneroMasculino = 0;
        int totalPCD = 0;
        
        for (IndicadorDiversidade ind : indicadores) {
            if (unidadeId == null || ind.getUnidadeOrganizacional().getId().equals(unidadeId)) {
                totalServidores += ind.getTotalServidores() != null ? ind.getTotalServidores() : 0;
                totalGeneroFeminino += ind.getQuantidadeGeneroFeminino() != null ? ind.getQuantidadeGeneroFeminino() : 0;
                totalGeneroMasculino += ind.getQuantidadeGeneroMasculino() != null ? ind.getQuantidadeGeneroMasculino() : 0;
                totalPCD += ind.getQuantidadePCD() != null ? ind.getQuantidadePCD() : 0;
            }
        }
        
        relatorio.put("totalServidores", totalServidores);
        relatorio.put("percentualFeminino", totalServidores > 0 ? (totalGeneroFeminino * 100.0 / totalServidores) : 0);
        relatorio.put("percentualMasculino", totalServidores > 0 ? (totalGeneroMasculino * 100.0 / totalServidores) : 0);
        relatorio.put("percentualPCD", totalServidores > 0 ? (totalPCD * 100.0 / totalServidores) : 0);
        
        return relatorio;
    }
    
    private void validarDados(IndicadorDiversidade indicador) {
        if (indicador.getTotalServidores() != null && indicador.getTotalServidores() < 0) {
            throw new BusinessException("O total de servidores não pode ser negativo");
        }
        // Validações adicionais podem ser implementadas conforme necessário
    }
}
