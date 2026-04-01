package com.pge.people.repository;

import com.pge.people.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    
    Optional<Employee> findByMatricula(String matricula);
    
    Optional<Employee> findByCpf(String cpf);
    
    @Query("SELECT e FROM Employee e WHERE e.ativo = true ORDER BY e.nome")
    List<Employee> findAllActive();
    
    @Query("SELECT e FROM Employee e WHERE e.tipoCargo = :tipoCargo AND e.ativo = true")
    List<Employee> findByCargoType(@Param("tipoCargo") String tipoCargo);
    
    @Query("SELECT e FROM Employee e WHERE e.lotacao.id = :lotacaoId AND e.ativo = true")
    List<Employee> findByLotacao(@Param("lotacaoId") Long lotacaoId);
    
    @Query("SELECT e FROM Employee e WHERE e.dataAdmissao BETWEEN :dataInicio AND :dataFim ORDER BY e.dataAdmissao")
    List<Employee> findEmployeesByAdmissionPeriod(@Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim);
    
    @Query("SELECT e FROM Employee e WHERE e.dataNascimento BETWEEN :dataInicio AND :dataFim")
    List<Employee> findEmployeesByBirthPeriod(@Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim);
    
    @Query("SELECT COUNT(e) FROM Employee e WHERE e.tipoCargo = :tipoCargo AND e.ativo = true")
    Long countByCargoType(@Param("tipoCargo") String tipoCargo);
    
    @Query("SELECT e FROM Employee e WHERE e.estagioProbatorio = true AND e.ativo = true")
    List<Employee> findProbationaryEmployees();
    
    @Query("SELECT e FROM Employee e WHERE (:genero IS NULL OR e.genero = :genero) " +
           "AND (:racaEtnia IS NULL OR e.racaEtnia = :racaEtnia) " +
           "AND (:pcd IS NULL OR e.pcd = :pcd) " +
           "AND e.ativo = true")
    List<Employee> findEmployeesByDiversityFilters(
        @Param("genero") String genero,
        @Param("racaEtnia") String racaEtnia,
        @Param("pcd") Boolean pcd
    );
    
    boolean existsByMatricula(String matricula);
    
    boolean existsByCpf(String cpf);
}
