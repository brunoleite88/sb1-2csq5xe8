package br.gov.pge.modulos.equidade.repository;

import br.gov.pge.modulos.equidade.entity.IndicadorDiversidade;
import br.gov.pge.modulos.equidade.entity.TipoIndicador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IndicadorDiversidadeRepository extends JpaRepository<IndicadorDiversidade, Long> {
    
    List<IndicadorDiversidade> findByUnidadeOrganizacionalId(Long unidadeId);
    
    List<IndicadorDiversidade> findByTipo(TipoIndicador tipo);
    
    List<IndicadorDiversidade> findByDataReferenciaBetween(LocalDateTime inicio, LocalDateTime fim);
    
    List<IndicadorDiversidade> findByAtivoTrue();
    
    @Query("SELECT i FROM IndicadorDiversidade i WHERE i.unidadeOrganizacional.id = :unidadeId AND i.ativo = true ORDER BY i.dataReferencia DESC")
    List<IndicadorDiversidade> findHistoricoPorUnidade(Long unidadeId);
    
    @Query("SELECT i.tipo, AVG(i.totalServidores) FROM IndicadorDiversidade i WHERE i.ativo = true GROUP BY i.tipo")
    List<Object[]> calcularMediasPorTipo();
}
