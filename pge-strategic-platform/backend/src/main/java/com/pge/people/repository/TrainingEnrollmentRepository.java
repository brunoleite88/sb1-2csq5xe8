package com.pge.people.repository;

import com.pge.people.entity.TrainingEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TrainingEnrollmentRepository extends JpaRepository<TrainingEnrollment, Long> {
    
    List<TrainingEnrollment> findByEmployeeId(Long employeeId);
    
    List<TrainingEnrollment> findByTrainingEventId(Long trainingEventId);
    
    @Query("SELECT te FROM TrainingEnrollment te WHERE te.employee.id = :employeeId ORDER BY te.dataInscricao DESC")
    List<TrainingEnrollment> findEnrollmentsByEmployee(@Param("employeeId") Long employeeId);
    
    @Query("SELECT te FROM TrainingEnrollment te WHERE te.trainingEvent.id = :trainingEventId")
    List<TrainingEnrollment> findEnrollmentsByEvent(@Param("trainingEventId") Long trainingEventId);
    
    @Query("SELECT te FROM TrainingEnrollment te WHERE te.employee.id = :employeeId AND te.status = 'CONFIRMADO'")
    List<TrainingEnrollment> findConfirmedEnrollmentsByEmployee(@Param("employeeId") Long employeeId);
    
    @Query("SELECT te FROM TrainingEnrollment te WHERE te.trainingEvent.id = :trainingEventId AND te.status = :status")
    List<TrainingEnrollment> findEnrollmentsByEventAndStatus(@Param("trainingEventId") Long trainingEventId, @Param("status") String status);
    
    @Query("SELECT te FROM TrainingEnrollment te WHERE te.employee.id = :employeeId AND te.certificadoEmitido = true")
    List<TrainingEnrollment> findCertifiedEnrollmentsByEmployee(@Param("employeeId") Long employeeId);
    
    @Query("SELECT COUNT(te) FROM TrainingEnrollment te WHERE te.trainingEvent.id = :trainingEventId AND te.status = 'CONFIRMADO'")
    Long countConfirmedEnrollments(@Param("trainingEventId") Long trainingEventId);
    
    @Query("SELECT te FROM TrainingEnrollment te WHERE te.dataConclusao BETWEEN :dataInicio AND :dataFim")
    List<TrainingEnrollment> findEnrollmentsByCompletionPeriod(@Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim);
    
    @Query("SELECT SUM(te.cargaHoraria) FROM TrainingEnrollment te WHERE te.employee.id = :employeeId AND te.status = 'CONCLUIDO'")
    Integer getTotalHoursByEmployee(@Param("employeeId") Long employeeId);
    
    Optional<TrainingEnrollment> findByEmployeeIdAndTrainingEventId(Long employeeId, Long trainingEventId);
    
    boolean existsByEmployeeIdAndTrainingEventId(Long employeeId, Long trainingEventId);
}
