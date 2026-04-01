package com.pge.people.repository;

import com.pge.people.entity.PerformanceEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PerformanceEvaluationRepository extends JpaRepository<PerformanceEvaluation, Long> {
    
    List<PerformanceEvaluation> findByEmployeeId(Long employeeId);
    
    List<PerformanceEvaluation> findByEmployeeIdAndAno(Long employeeId, Integer ano);
    
    @Query("SELECT pe FROM PerformanceEvaluation pe WHERE pe.employee.id = :employeeId ORDER BY pe.dataAvaliacao DESC")
    List<PerformanceEvaluation> findEvaluationsByEmployee(@Param("employeeId") Long employeeId);
    
    @Query("SELECT pe FROM PerformanceEvaluation pe WHERE pe.ano = :ano AND pe.tipoAvaliacao = :tipoAvaliacao")
    List<PerformanceEvaluation> findEvaluationsByYearAndType(@Param("ano") Integer ano, @Param("tipoAvaliacao") String tipoAvaliacao);
    
    @Query("SELECT pe FROM PerformanceEvaluation pe WHERE pe.employee.id = :employeeId AND pe.dataAvaliacao >= :dataInicio")
    List<PerformanceEvaluation> findRecentEvaluations(@Param("employeeId") Long employeeId, @Param("dataInicio") LocalDate dataInicio);
    
    @Query("SELECT pe FROM PerformanceEvaluation pe WHERE pe.responsavelAvaliador.id = :avaliadorId AND pe.ano = :ano")
    List<PerformanceEvaluation> findEvaluationsByEvaluator(@Param("avaliadorId") Long avaliadorId, @Param("ano") Integer ano);
    
    @Query("SELECT AVG(pe.notaFinal) FROM PerformanceEvaluation pe WHERE pe.ano = :ano AND pe.tipoAvaliacao = :tipoAvaliacao")
    Double findAverageScoreByYearAndType(@Param("ano") Integer ano, @Param("tipoAvaliacao") String tipoAvaliacao);
    
    @Query("SELECT pe FROM PerformanceEvaluation pe WHERE pe.status = 'PENDENTE' AND pe.dataLimite < :dataAtual")
    List<PerformanceEvaluation> findOverdueEvaluations(@Param("dataAtual") LocalDate dataAtual);
    
    Optional<PerformanceEvaluation> findByEmployeeIdAndAnoAndTipoAvaliacao(Long employeeId, Integer ano, String tipoAvaliacao);
}
