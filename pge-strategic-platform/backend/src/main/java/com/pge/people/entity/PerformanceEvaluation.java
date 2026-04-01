package com.pge.people.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.math.BigDecimal;

@Entity
@Table(name = "performance_evaluation")
public class PerformanceEvaluation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;
    
    @Column(nullable = false)
    private LocalDate evaluationDate;
    
    @Column(length = 20)
    private String evaluationType; // MENSAL, SEMESTRAL, ANUAL, 360
    
    @Column(precision = 10, scale = 2)
    private BigDecimal judicialActsCount; // Atos judiciais
    
    @Column(precision = 10, scale = 2)
    private BigDecimal administrativeActsCount; // Atos administrativos
    
    @Column(precision = 10, scale = 2)
    private BigDecimal completedProcessesCount; // Processos concluídos
    
    @Column(precision = 5, scale = 2)
    private BigDecimal deadlineComplianceRate; // % de prazos cumpridos
    
    @Column(precision = 5, scale = 2)
    private BigDecimal qualityScore; // Qualidade técnica
    
    @Column(precision = 5, scale = 2)
    private BigDecimal trainingHours; // Horas de capacitação
    
    @Column(precision = 5, scale = 2)
    private BigDecimal overallScore; // Pontuação geral
    
    @Column(length = 2000)
    private String evaluatorComments;
    
    @Column(length = 20)
    private String status; // APROVADO, REVISAO, PENDENTE
    
    @Column(length = 100)
    private String evaluatorName;
    
    @Column(name = "evaluator_role", length = 100)
    private String evaluatorRole;
    
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
    public LocalDate getEvaluationDate() { return evaluationDate; }
    public void setEvaluationDate(LocalDate evaluationDate) { this.evaluationDate = evaluationDate; }
    public String getEvaluationType() { return evaluationType; }
    public void setEvaluationType(String evaluationType) { this.evaluationType = evaluationType; }
    public BigDecimal getJudicialActsCount() { return judicialActsCount; }
    public void setJudicialActsCount(BigDecimal judicialActsCount) { this.judicialActsCount = judicialActsCount; }
    public BigDecimal getAdministrativeActsCount() { return administrativeActsCount; }
    public void setAdministrativeActsCount(BigDecimal administrativeActsCount) { this.administrativeActsCount = administrativeActsCount; }
    public BigDecimal getCompletedProcessesCount() { return completedProcessesCount; }
    public void setCompletedProcessesCount(BigDecimal completedProcessesCount) { this.completedProcessesCount = completedProcessesCount; }
    public BigDecimal getDeadlineComplianceRate() { return deadlineComplianceRate; }
    public void setDeadlineComplianceRate(BigDecimal deadlineComplianceRate) { this.deadlineComplianceRate = deadlineComplianceRate; }
    public BigDecimal getQualityScore() { return qualityScore; }
    public void setQualityScore(BigDecimal qualityScore) { this.qualityScore = qualityScore; }
    public BigDecimal getTrainingHours() { return trainingHours; }
    public void setTrainingHours(BigDecimal trainingHours) { this.trainingHours = trainingHours; }
    public BigDecimal getOverallScore() { return overallScore; }
    public void setOverallScore(BigDecimal overallScore) { this.overallScore = overallScore; }
    public String getEvaluatorComments() { return evaluatorComments; }
    public void setEvaluatorComments(String evaluatorComments) { this.evaluatorComments = evaluatorComments; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getEvaluatorName() { return evaluatorName; }
    public void setEvaluatorName(String evaluatorName) { this.evaluatorName = evaluatorName; }
    public String getEvaluatorRole() { return evaluatorRole; }
    public void setEvaluatorRole(String evaluatorRole) { this.evaluatorRole = evaluatorRole; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
    public LocalDate getCreatedAt() { return createdAt; }
}
