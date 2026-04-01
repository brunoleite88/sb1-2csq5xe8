package com.pge.people.repository;

import com.pge.people.entity.AttendanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRecordRepository extends JpaRepository<AttendanceRecord, Long> {
    
    List<AttendanceRecord> findByEmployeeId(Long employeeId);
    
    @Query("SELECT ar FROM AttendanceRecord ar WHERE ar.employee.id = :employeeId AND ar.dataRegistro >= :dataInicio AND ar.dataRegistro <= :dataFim ORDER BY ar.dataRegistro DESC")
    List<AttendanceRecord> findRecordsByEmployeeAndPeriod(@Param("employeeId") Long employeeId, @Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim);
    
    @Query("SELECT ar FROM AttendanceRecord ar WHERE ar.dataRegistro = :data AND ar.employee.tipoCargo = :tipoCargo")
    List<AttendanceRecord> findRecordsByDateAndCargoType(@Param("data") LocalDate data, @Param("tipoCargo") String tipoCargo);
    
    @Query("SELECT ar FROM AttendanceRecord ar WHERE ar.employee.id = :employeeId AND ar.tipoRegistro = 'TELETRABALHO' ORDER BY ar.dataRegistro DESC")
    List<AttendanceRecord> findTeleworkRecordsByEmployee(@Param("employeeId") Long employeeId);
    
    @Query("SELECT COUNT(ar) FROM AttendanceRecord ar WHERE ar.employee.id = :employeeId AND ar.dataRegistro >= :dataInicio AND ar.dataRegistro <= :dataFim")
    Long countRecordsByEmployeeAndPeriod(@Param("employeeId") Long employeeId, @Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim);
    
    @Query("SELECT ar FROM AttendanceRecord ar WHERE ar.justificativa IS NOT NULL AND ar.status = 'PENDENTE_APROVACAO'")
    List<AttendanceRecord> findPendingJustifications();
    
    @Query("SELECT ar FROM AttendanceRecord ar WHERE ar.employee.id = :employeeId AND ar.dataRegistro BETWEEN :dataInicio AND :dataFim AND ar.tipoAusencia IS NOT NULL")
    List<AttendanceRecord> findAbsencesByEmployeeAndPeriod(@Param("employeeId") Long employeeId, @Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim);
    
    Optional<AttendanceRecord> findByEmployeeIdAndDataRegistro(Long employeeId, LocalDate dataRegistro);
}
