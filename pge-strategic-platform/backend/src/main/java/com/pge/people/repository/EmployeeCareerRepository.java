package com.pge.people.repository;

import com.pge.people.entity.EmployeeCareer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeCareerRepository extends JpaRepository<EmployeeCareer, Long> {
    
    Optional<EmployeeCareer> findByEmployeeId(Long employeeId);
    
    @Query("SELECT ec FROM EmployeeCareer ec WHERE ec.employee.id = :employeeId ORDER BY ec.dataInicio DESC")
    List<EmployeeCareer> findCareerHistoryByEmployee(@Param("employeeId") Long employeeId);
    
    @Query("SELECT ec FROM EmployeeCareer ec JOIN ec.careerStage cs WHERE cs.codigo = :stageCodigo AND ec.dataFim IS NULL")
    List<EmployeeCareer> findActiveCareersByStage(@Param("stageCodigo") String stageCodigo);
    
    @Query("SELECT ec FROM EmployeeCareer ec WHERE ec.employee.id = :employeeId AND ec.dataFim IS NULL")
    Optional<EmployeeCareer> findCurrentCareer(@Param("employeeId") Long employeeId);
    
    @Query("SELECT ec FROM EmployeeCareer ec WHERE ec.employee.id = :employeeId AND ec.tipoMovimentacao = 'PROMOCAO'")
    List<EmployeeCareer> findPromotionsByEmployee(@Param("employeeId") Long employeeId);
    
    @Query("SELECT COUNT(ec) FROM EmployeeCareer ec WHERE ec.careerStage.nivel <= :nivelMax AND ec.dataFim IS NULL")
    Long countActiveEmployeesByMaxLevel(@Param("nivelMax") Integer nivelMax);
    
    @Query("SELECT ec FROM EmployeeCareer ec WHERE ec.dataInicio BETWEEN :dataInicio AND :dataFim ORDER BY ec.dataInicio")
    List<EmployeeCareer> findCareersByPeriod(@Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim);
    
    boolean existsByEmployeeIdAndDataFimIsNull(Long employeeId);
}
