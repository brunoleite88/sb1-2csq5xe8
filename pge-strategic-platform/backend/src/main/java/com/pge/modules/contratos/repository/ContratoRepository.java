package com.pge.modules.contratos.repository;

import com.pge.modules.contratos.entity.Contrato;
import com.pge.modules.contratos.entity.Contrato.StatusContrato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ContratoRepository extends JpaRepository<Contrato, Long> {
    
    List<Contrato> findByStatus(StatusContrato status);
    
    List<Contrato> findByFornecedorContainingIgnoreCase(String fornecedor);
    
    @Query("SELECT c FROM Contrato c WHERE c.status = :status AND c.dataFim BETWEEN :inicio AND :fim")
    List<Contrato> findContratosAVencer(@Param("status") StatusContrato status, 
                                         @Param("inicio") LocalDate inicio, 
                                         @Param("fim") LocalDate fim);
    
    @Query("SELECT c FROM Contrato c WHERE c.alertaVencimento90 = true AND c.status = 'ATIVO'")
    List<Contrato> findContratosAlerta90Dias();
    
    @Query("SELECT c FROM Contrato c WHERE c.alertaVencimento30 = true AND c.status = 'ATIVO'")
    List<Contrato> findContratosAlerta30Dias();
    
    @Query("SELECT c FROM Contrato c WHERE c.gestorContrato = :gestor AND c.status = 'ATIVO'")
    List<Contrato> findContratosPorGestor(@Param("gestor") String gestor);
    
    boolean existsByNumeroContratoAndAno(String numeroContrato, Integer ano);
    
    long countByStatus(StatusContrato status);
}
