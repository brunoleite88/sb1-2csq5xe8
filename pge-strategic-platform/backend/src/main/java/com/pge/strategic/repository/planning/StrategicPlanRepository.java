package com.pge.strategic.repository.planning;

import com.pge.strategic.model.planning.StrategicPlan;
import com.pge.strategic.model.planning.PlanStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StrategicPlanRepository extends JpaRepository<StrategicPlan, Long> {

    List<StrategicPlan> findByStatus(PlanStatus status);

    List<StrategicPlan> findByStartYearAndEndYear(Integer startYear, Integer endYear);

    Optional<StrategicPlan> findByTitle(String title);

    @Query("SELECT sp FROM StrategicPlan sp WHERE sp.status = :status ORDER BY sp.createdAt DESC")
    List<StrategicPlan> findByStatusOrdered(@Param("status") PlanStatus status);

    @Query("SELECT sp FROM StrategicPlan sp WHERE sp.createdBy.id = :userId")
    List<StrategicPlan> findByCreatedBy(@Param("userId") Long userId);

    boolean existsByTitleAndStartYearAndEndYear(String title, Integer startYear, Integer endYear);
}
