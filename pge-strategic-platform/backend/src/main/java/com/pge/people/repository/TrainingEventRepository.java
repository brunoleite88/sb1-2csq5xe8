package com.pge.people.repository;

import com.pge.people.entity.TrainingEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TrainingEventRepository extends JpaRepository<TrainingEvent, Long> {
    
    List<TrainingEvent> findByStatus(String status);
    
    @Query("SELECT te FROM TrainingEvent te WHERE te.status = 'ATIVO' ORDER BY te.dataInicio DESC")
    List<TrainingEvent> findActiveEvents();
    
    @Query("SELECT te FROM TrainingEvent te WHERE te.esapId IS NOT NULL ORDER BY te.dataInicio DESC")
    List<TrainingEvent> findEsapEvents();
    
    @Query("SELECT te FROM TrainingEvent te WHERE te.tipoEvento = :tipoEvento AND te.status = 'ATIVO'")
    List<TrainingEvent> findByEventType(@Param("tipoEvento") String tipoEvento);
    
    @Query("SELECT te FROM TrainingEvent te WHERE te.dataInicio BETWEEN :dataInicio AND :dataFim ORDER BY te.dataInicio")
    List<TrainingEvent> findEventsByPeriod(@Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim);
    
    @Query("SELECT te FROM TrainingEvent te WHERE te.responsavel.id = :instrutorId")
    List<TrainingEvent> findEventsByInstructor(@Param("instrutorId") Long instrutorId);
    
    @Query("SELECT te FROM TrainingEvent te JOIN te.enrollments e WHERE e.employee.id = :employeeId ORDER BY te.dataInicio DESC")
    List<TrainingEvent> findEventsByEmployee(@Param("employeeId") Long employeeId);
    
    @Query("SELECT COUNT(te) FROM TrainingEvent te WHERE te.status = 'ATIVO' AND te.tipoEvento = :tipoEvento")
    Long countActiveEventsByType(@Param("tipoEvento") String tipoEvento);
    
    @Query("SELECT te FROM TrainingEvent te WHERE te.certificadoDigital = true AND te.status = 'CONCLUIDO'")
    List<TrainingEvent> findEventsWithDigitalCertificate();
    
    Optional<TrainingEvent> findByEsapId(Long esapId);
}
