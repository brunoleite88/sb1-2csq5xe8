package com.pge.people.repository;

import com.pge.people.entity.MovementRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MovementRequestRepository extends JpaRepository<MovementRequest, Long> {
    
    List<MovementRequest> findByEmployeeId(Long employeeId);
    
    @Query("SELECT mr FROM MovementRequest mr WHERE mr.employee.id = :employeeId ORDER BY mr.dataSolicitacao DESC")
    List<MovementRequest> findRequestsByEmployee(@Param("employeeId") Long employeeId);
    
    @Query("SELECT mr FROM MovementRequest mr WHERE mr.tipoMovimentacao = :tipoMovimentacao AND mr.status = :status")
    List<MovementRequest> findRequestsByTypeAndStatus(@Param("tipoMovimentacao") String tipoMovimentacao, @Param("status") String status);
    
    @Query("SELECT mr FROM MovementRequest mr WHERE mr.status = 'PENDENTE' ORDER BY mr.dataSolicitacao ASC")
    List<MovementRequest> findPendingRequests();
    
    @Query("SELECT mr FROM MovementRequest mr WHERE mr.lotacaoOrigem.id = :lotacaoId OR mr.lotacaoDestino.id = :lotacaoId")
    List<MovementRequest> findRequestsByLotacao(@Param("lotacaoId") Long lotacaoId);
    
    @Query("SELECT mr FROM MovementRequest mr WHERE mr.dataSolicitacao BETWEEN :dataInicio AND :dataFim ORDER BY mr.dataSolicitacao")
    List<MovementRequest> findRequestsByPeriod(@Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim);
    
    @Query("SELECT COUNT(mr) FROM MovementRequest mr WHERE mr.tipoMovimentacao = :tipoMovimentacao AND mr.status = 'APROVADO'")
    Long countApprovedMovementsByType(@Param("tipoMovimentacao") String tipoMovimentacao);
    
    @Query("SELECT mr FROM MovementRequest mr WHERE mr.aprovadorResponsavel.id = :aprovadorId AND mr.status = 'PENDENTE'")
    List<MovementRequest> findRequestsPendingApproval(@Param("aprovadorId") Long aprovadorId);
    
    Optional<MovementRequest> findByNumeroProcesso(String numeroProcesso);
}
