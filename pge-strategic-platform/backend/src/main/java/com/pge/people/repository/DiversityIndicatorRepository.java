package com.pge.people.repository;

import com.pge.people.entity.DiversityIndicator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DiversityIndicatorRepository extends JpaRepository<DiversityIndicator, Long> {
    
    List<DiversityIndicator> findByAno(Integer ano);
    
    @Query("SELECT di FROM DiversityIndicator di WHERE di.ano = :ano AND di.periodoReferencia = :periodo ORDER BY di.dataCriacao DESC")
    List<DiversityIndicator> findIndicatorsByYearAndPeriod(@Param("ano") Integer ano, @Param("periodo") String periodo);
    
    @Query("SELECT di FROM DiversityIndicator di WHERE di.lotacao.id = :lotacaoId AND di.ano = :ano ORDER BY di.dataCriacao DESC")
    List<DiversityIndicator> findIndicatorsByLotacaoAndYear(@Param("lotacaoId") Long lotacaoId, @Param("ano") Integer ano);
    
    @Query("SELECT di FROM DiversityIndicator di WHERE di.ano = :ano ORDER BY di.percentualGeneroFeminino ASC")
    List<DiversityIndicator> findIndicatorsByYearOrderByGender(@Param("ano") Integer ano);
    
    @Query("SELECT AVG(di.percentualPcd) FROM DiversityIndicator di WHERE di.ano = :ano")
    Double getAveragePcdPercentageByYear(@Param("ano") Integer ano);
    
    @Query("SELECT AVG(di.percentualNegrosPardos) FROM DiversityIndicator di WHERE di.ano = :ano")
    Double getAverageRacePercentageByYear(@Param("ano") Integer ano);
    
    @Query("SELECT di FROM DiversityIndicator di WHERE di.dataCriacao BETWEEN :dataInicio AND :dataFim ORDER BY di.dataCriacao")
    List<DiversityIndicator> findIndicatorsByPeriod(@Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim);
    
    @Query("SELECT COUNT(di) FROM DiversityIndicator di WHERE di.ano = :ano AND di.lotacao.id = :lotacaoId")
    Long countIndicatorsByYearAndLotacao(@Param("ano") Integer ano, @Param("lotacaoId") Long lotacaoId);
    
    Optional<DiversityIndicator> findTopByAnoAndLotacaoIdOrderByDataCriacaoDesc(Integer ano, Long lotacaoId);
}
