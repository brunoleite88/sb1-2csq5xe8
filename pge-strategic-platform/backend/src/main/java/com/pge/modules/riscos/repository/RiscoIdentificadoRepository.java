package com.pge.modules.riscos.repository;

import com.pge.modules.riscos.entity.RiscoIdentificado;
import com.pge.modules.riscos.entity.RiscoIdentificado.CategoriaRisco;
import com.pge.modules.riscos.entity.RiscoIdentificado.StatusRisco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RiscoIdentificadoRepository extends JpaRepository<RiscoIdentificado, Long> {
    
    List<RiscoIdentificado> findByStatus(StatusRisco status);
    
    List<RiscoIdentificado> findByCategoria(CategoriaRisco categoria);
    
    @Query("SELECT r FROM RiscoIdentificado r WHERE r.nivelRisco >= :nivelMinimo ORDER BY r.nivelRisco DESC")
    List<RiscoIdentificado> findRiscosPorNivel(@Param("nivelMinimo") Integer nivelMinimo);
    
    @Query("SELECT r FROM RiscoIdentificado r WHERE r.responsavel = :responsavel AND r.status NOT IN :statusExcluidos")
    List<RiscoIdentificado> findRiscosPorResponsavel(@Param("responsavel") String responsavel, 
                                                      @Param("statusExcluidos") List<StatusRisco> statusExcluidos);
    
    @Query("SELECT r FROM RiscoIdentificado r WHERE r.status = 'EM_TRATAMENTO' AND r.dataPrevistaMitigacao < CURRENT_DATE")
    List<RiscoIdentificado> findRiscosAtrasados();
    
    long countByNivelRiscoGreaterThanEqual(Integer nivelMinimo);
    
    long countByStatus(StatusRisco status);
}
