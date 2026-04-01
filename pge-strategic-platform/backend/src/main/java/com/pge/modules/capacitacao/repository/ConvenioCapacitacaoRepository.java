package com.pge.modules.capacitacao.repository;

import com.pge.modules.capacitacao.entity.ConvenioCapacitacao;
import com.pge.modules.capacitacao.entity.ConvenioCapacitacao.StatusConvenio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ConvenioCapacitacaoRepository extends JpaRepository<ConvenioCapacitacao, Long> {
    
    List<ConvenioCapacitacao> findByStatus(StatusConvenio status);
    
    List<ConvenioCapacitacao> findByInstituicaoContainingIgnoreCase(String instituicao);
    
    @Query("SELECT c FROM ConvenioCapacitacao c WHERE c.status = :status AND c.dataFim >= :dataAtual")
    List<ConvenioCapacitacao> findConveniosAtivos(@Param("status") StatusConvenio status, 
                                                   @Param("dataAtual") LocalDate dataAtual);
    
    @Query("SELECT c FROM ConvenioCapacitacao c WHERE c.dataFim BETWEEN :inicio AND :fim")
    List<ConvenioCapacitacao> findConveniosAVencer(@Param("inicio") LocalDate inicio, 
                                                    @Param("fim") LocalDate fim);
    
    boolean existsByInstituicaoAndNumeroProcesso(String instituicao, String numeroProcesso);
}
