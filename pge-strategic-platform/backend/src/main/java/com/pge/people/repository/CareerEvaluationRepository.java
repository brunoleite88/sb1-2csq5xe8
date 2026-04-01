package com.pge.people.repository;

import com.pge.people.entity.CareerEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CareerEvaluationRepository extends JpaRepository<CareerEvaluation, Long> {
    
    List<CareerEvaluation> findByEmployeeId(Long employeeId);
    
    List<CareerEvaluation> findByEmployeeIdAndAno(Long employeeId, Integer ano);
    
    @Query("SELECT ce FROM CareerEvaluation ce WHERE ce.employee.id = :employeeId AND ce.ano = :ano ORDER BY ce.dataAvaliacao DESC")
    List<CareerEvaluation> findEvaluationsByEmployeeAndYear(@Param("employeeId") Long employeeId, @Param("ano") Integer ano);
    
    @Query("SELECT ce FROM CareerEvaluation ce WHERE ce.tipoAvaliacao = 'ESTAGIO_PROBATORIO' AND ce.employee.id = :employeeId")
    List<CareerEvaluation> findProbationaryEvaluations(@Param("employeeId") Long employeeId);
    
    @Query("SELECT ce FROM CareerEvaluation ce WHERE ce.tipoAvaliacao = 'MERECIMENTO' AND ce.ano = :ano")
    List<CareerEvaluation> findMeritEvaluationsByYear(@Param("ano") Integer ano);
    
    @Query("SELECT ce FROM CareerEvaluation ce WHERE ce.employee.id = :employeeId AND ce.dataAvaliacao >= :dataInicio")
    List<CareerEvaluation> findRecentEvaluations(@Param("employeeId") Long employeeId, @Param("dataInicio") LocalDate dataInicio);
    
    Optional<CareerEvaluation> findByEmployeeIdAndTipoAvaliacaoAndAno(Long employeeId, String tipoAvaliacao, Integer ano);
    
    @Query("SELECT COUNT(ce) FROM CareerEvaluation ce WHERE ce.pontuacaoTotal >= :pontuacaoMinima AND ce.ano = :ano")
    Long countEvaluationsWithMinimumScore(@Param("pontuacaoMinima") Double pontuacaoMinima, @Param("ano") Integer ano);
}
