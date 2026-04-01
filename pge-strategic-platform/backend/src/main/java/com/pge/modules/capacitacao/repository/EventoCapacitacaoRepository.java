package com.pge.modules.capacitacao.repository;

import com.pge.modules.capacitacao.entity.EventoCapacitacao;
import com.pge.modules.capacitacao.entity.EventoCapacitacao.StatusEvento;
import com.pge.modules.capacitacao.entity.EventoCapacitacao.TipoEvento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventoCapacitacaoRepository extends JpaRepository<EventoCapacitacao, Long> {
    
    List<EventoCapacitacao> findByStatus(StatusEvento status);
    
    List<EventoCapacitacao> findByTipo(TipoEvento tipo);
    
    List<EventoCapacitacao> findByDataInicioBetween(LocalDate inicio, LocalDate fim);
    
    @Query("SELECT e FROM EventoCapacitacao e WHERE e.status = :status AND e.dataInicio >= :dataAtual")
    List<EventoCapacitacao> findEventosFuturos(@Param("status") StatusEvento status, 
                                                @Param("dataAtual") LocalDate dataAtual);
    
    @Query("SELECT e FROM EventoCapacitacao e WHERE e.status = 'EM_ANDAMENTO' AND e.dataFim >= :dataAtual")
    List<EventoCapacitacao> findEventosEmAndamento(@Param("dataAtual") LocalDate dataAtual);
    
    boolean existsByTituloAndDataInicio(String titulo, LocalDate dataInicio);
}
