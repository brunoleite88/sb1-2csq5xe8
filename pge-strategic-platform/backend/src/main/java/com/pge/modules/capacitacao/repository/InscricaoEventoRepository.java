package com.pge.modules.capacitacao.repository;

import com.pge.modules.capacitacao.entity.InscricaoEvento;
import com.pge.modules.capacitacao.entity.InscricaoEvento.StatusInscricao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface InscricaoEventoRepository extends JpaRepository<InscricaoEvento, Long> {
    
    List<InscricaoEvento> findByEventoId(Long eventoId);
    
    List<InscricaoEvento> findByServidorId(Long servidorId);
    
    List<InscricaoEvento> findByStatus(StatusInscricao status);
    
    @Query("SELECT i FROM InscricaoEvento i WHERE i.servidorId = :servidorId AND i.status = :status")
    List<InscricaoEvento> findInscricoesAtivasPorServidor(@Param("servidorId") Long servidorId, 
                                                           @Param("status") StatusInscricao status);
    
    @Query("SELECT i FROM InscricaoEvento i WHERE i.evento.id = :eventoId AND i.compareceu = true")
    List<InscricaoEvento> findParticipantesConfirmados(@Param("eventoId") Long eventoId);
    
    @Query("SELECT i FROM InscricaoEvento i WHERE i.emitiuCertificado = false AND i.compareceu = true")
    List<InscricaoEvento> findCertificadosPendentes();
    
    boolean existsByEventoIdAndServidorId(Long eventoId, Long servidorId);
    
    long countByEventoIdAndCompareceuTrue(Long eventoId);
    
    long countByServidorIdAndCompareceuTrue(Long servidorId);
}
