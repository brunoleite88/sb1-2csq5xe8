package com.pge.people.repository;

import com.pge.people.entity.CareerStage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CareerStageRepository extends JpaRepository<CareerStage, Long> {
    
    Optional<CareerStage> findByCodigo(String codigo);
    
    List<CareerStage> findAllByOrderByNivelAsc();
    
    @Query("SELECT cs FROM CareerStage cs WHERE cs.ativo = true ORDER BY cs.nivel ASC")
    List<CareerStage> findActiveStages();
    
    @Query("SELECT cs FROM CareerStage cs WHERE cs.classe = :classe AND cs.ativo = true")
    Optional<CareerStage> findByClasseAndActive(@Param("classe") String classe);
    
    boolean existsByCodigo(String codigo);
}
