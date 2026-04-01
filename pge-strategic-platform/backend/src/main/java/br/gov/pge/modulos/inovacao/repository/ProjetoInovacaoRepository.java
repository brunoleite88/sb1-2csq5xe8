package br.gov.pge.modulos.inovacao.repository;

import br.gov.pge.modulos.inovacao.entity.ProjetoInovacao;
import br.gov.pge.modulos.inovacao.entity.EtapaInovacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjetoInovacaoRepository extends JpaRepository<ProjetoInovacao, Long> {
    
    List<ProjetoInovacao> findByEtapaAtual(EtapaInovacao etapa);
    
    List<ProjetoInovacao> findByResponsavelId(Long responsavelId);
    
    List<ProjetoInovacao> findByUnidadeOrganizacionalId(Long unidadeId);
    
    List<ProjetoInovacao> findByAtivoTrue();
    
    @Query("SELECT p FROM ProjetoInovacao p WHERE p.indicaPremioInovacao = true")
    List<ProjetoInovacao> findProjetosIndicadosPremio();
    
    @Query("SELECT p.etapaAtual, COUNT(p) FROM ProjetoInovacao p WHERE p.ativo = true GROUP BY p.etapaAtual")
    List<Object[]> countPorEtapa();
}
