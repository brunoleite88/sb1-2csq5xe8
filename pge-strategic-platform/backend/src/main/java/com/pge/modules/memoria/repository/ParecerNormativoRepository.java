package com.pge.modules.memoria.repository;

import com.pge.modules.memoria.entity.ParecerNormativo;
import com.pge.modules.memoria.entity.ParecerNormativo.StatusParecer;
import com.pge.modules.memoria.entity.ParecerNormativo.TipoParecer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParecerNormativoRepository extends JpaRepository<ParecerNormativo, Long> {
    
    List<ParecerNormativo> findByStatus(StatusParecer status);
    
    List<ParecerNormativo> findByTipo(TipoParecer tipo);
    
    List<ParecerNormativo> findByAno(Integer ano);
    
    @Query("SELECT p FROM ParecerNormativo p WHERE p.status = 'VIGENTE' AND (:assunto IS NULL OR p.assunto LIKE %:assunto%)")
    List<ParecerNormativo> findPareceresVigentesPorAssunto(@Param("assunto") String assunto);
    
    @Query("SELECT p FROM ParecerNormativo p WHERE p.numeroParecer = :numero AND p.ano = :ano")
    ParecerNormativo findByNumeroEAno(@Param("numero") String numero, @Param("ano") Integer ano);
    
    boolean existsByNumeroParecerAndAno(String numeroParecer, Integer ano);
}
