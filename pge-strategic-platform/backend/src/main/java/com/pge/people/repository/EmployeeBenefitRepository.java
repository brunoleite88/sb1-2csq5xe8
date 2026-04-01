package com.pge.people.repository;

import com.pge.people.entity.EmployeeBenefit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeBenefitRepository extends JpaRepository<EmployeeBenefit, Long> {
    
    List<EmployeeBenefit> findByEmployeeId(Long employeeId);
    
    @Query("SELECT eb FROM EmployeeBenefit eb WHERE eb.employee.id = :employeeId AND eb.ativo = true")
    List<EmployeeBenefit> findActiveBenefitsByEmployee(@Param("employeeId") Long employeeId);
    
    @Query("SELECT eb FROM EmployeeBenefit eb WHERE eb.tipoBeneficio = :tipoBeneficio AND eb.ativo = true")
    List<EmployeeBenefit> findBenefitsByType(@Param("tipoBeneficio") String tipoBeneficio);
    
    @Query("SELECT eb FROM EmployeeBenefit eb WHERE eb.employee.id = :employeeId AND eb.tipoBeneficio = :tipoBeneficio AND eb.ativo = true")
    Optional<EmployeeBenefit> findActiveBenefitByEmployeeAndType(@Param("employeeId") Long employeeId, @Param("tipoBeneficio") String tipoBeneficio);
    
    @Query("SELECT eb FROM EmployeeBenefit eb WHERE eb.dataInicio BETWEEN :dataInicio AND :dataFim ORDER BY eb.dataInicio")
    List<EmployeeBenefit> findBenefitsByPeriod(@Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim);
    
    @Query("SELECT COUNT(eb) FROM EmployeeBenefit eb WHERE eb.tipoBeneficio = :tipoBeneficio AND eb.ativo = true")
    Long countActiveBenefitsByType(@Param("tipoBeneficio") String tipoBeneficio);
    
    @Query("SELECT SUM(eb.valorMensal) FROM EmployeeBenefit eb WHERE eb.tipoBeneficio = :tipoBeneficio AND eb.ativo = true")
    Double getTotalMonthlyCostByBenefitType(@Param("tipoBeneficio") String tipoBeneficio);
    
    @Query("SELECT eb FROM EmployeeBenefit eb WHERE eb.dataFim IS NOT NULL AND eb.dataFim < :dataAtual AND eb.ativo = true")
    List<EmployeeBenefit> findExpiredBenefits(@Param("dataAtual") LocalDate dataAtual);
}
