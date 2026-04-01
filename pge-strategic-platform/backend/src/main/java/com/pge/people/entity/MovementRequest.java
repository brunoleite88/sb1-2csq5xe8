package com.pge.people.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "movement_request")
public class MovementRequest {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;
    
    @Column(nullable = false, length = 50)
    private String movementType; // REMOCAO_VOLUNTARIA, REMOCAO_COMPULSORIA, MOVIMENTACAO_ESPECIALIZADA, REDISTRIBUICAO, LOTACAO_PROVISORIA
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "origin_unit_id")
    private OrganizationalUnit originUnit;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_unit_id")
    private OrganizationalUnit destinationUnit;
    
    @Column(nullable = false)
    private LocalDate requestDate;
    
    @Column
    private LocalDate expectedDate;
    
    @Column(length = 20)
    private String status; // PENDENTE, EM_ANALISE, APROVADO, REPROVADO, CANCELADO, EFETIVADO
    
    @Column(length = 100)
    private String justification;
    
    @Column(length = 500)
    private String legalBasis; // Fundamentação legal (Art. 39 LC 20/1994, etc.)
    
    @Column(length = 100)
    private String approvalAct; // Ato de aprovação
    
    @Column(length = 100)
    private String approvedBy;
    
    @Column
    private LocalDate approvalDate;
    
    @Column(length = 2000)
    private String observations;
    
    @Column(nullable = false)
    private Boolean active = true;
    
    @Column(name = "created_at")
    private LocalDate createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDate.now();
    }
    
    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Employee getEmployee() { return employee; }
    public void setEmployee(Employee employee) { this.employee = employee; }
    public String getMovementType() { return movementType; }
    public void setMovementType(String movementType) { this.movementType = movementType; }
    public OrganizationalUnit getOriginUnit() { return originUnit; }
    public void setOriginUnit(OrganizationalUnit originUnit) { this.originUnit = originUnit; }
    public OrganizationalUnit getDestinationUnit() { return destinationUnit; }
    public void setDestinationUnit(OrganizationalUnit destinationUnit) { this.destinationUnit = destinationUnit; }
    public LocalDate getRequestDate() { return requestDate; }
    public void setRequestDate(LocalDate requestDate) { this.requestDate = requestDate; }
    public LocalDate getExpectedDate() { return expectedDate; }
    public void setExpectedDate(LocalDate expectedDate) { this.expectedDate = expectedDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getJustification() { return justification; }
    public void setJustification(String justification) { this.justification = justification; }
    public String getLegalBasis() { return legalBasis; }
    public void setLegalBasis(String legalBasis) { this.legalBasis = legalBasis; }
    public String getApprovalAct() { return approvalAct; }
    public void setApprovalAct(String approvalAct) { this.approvalAct = approvalAct; }
    public String getApprovedBy() { return approvedBy; }
    public void setApprovedBy(String approvedBy) { this.approvedBy = approvedBy; }
    public LocalDate getApprovalDate() { return approvalDate; }
    public void setApprovalDate(LocalDate approvalDate) { this.approvalDate = approvalDate; }
    public String getObservations() { return observations; }
    public void setObservations(String observations) { this.observations = observations; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
    public LocalDate getCreatedAt() { return createdAt; }
}
