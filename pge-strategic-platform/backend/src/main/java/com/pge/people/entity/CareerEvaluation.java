package com.pge.people.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.math.BigDecimal;

@Entity
@Table(name = "career_evaluation")
public class CareerEvaluation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "career_stage_id", nullable = false)
    private CareerStage careerStage;
    
    @Column(nullable = false)
    private LocalDate evaluationDate;
    
    @Column(length = 20)
    private String evaluationType; // PROBATÓRIO, MÉRITO, ANTIGUIDADE
    
    @Column(precision = 5, scale = 2)
    private BigDecimal totalScore; // Pontuação total (0-100)
    
    @Column(precision = 5, scale = 2)
    private BigDecimal competencyScore; // Competência Profissional (max 20)
    
    @Column(precision = 5, scale = 2)
    private BigDecimal efficiencyScore; // Eficiência no Exercício (max 30)
    
    @Column(precision = 5, scale = 2)
    private BigDecimal dedicationScore; // Dedicação e Pontualidade (max 25)
    
    @Column(precision = 5, scale = 2)
    private BigDecimal culturalScore; // Aprimoramento Cultural (max 10)
    
    @Column(precision = 5, scale = 2)
    private BigDecimal ethicalScore; // Conduta Ética (max 15)
    
    @Column(length = 2000)
    private String evaluatorComments;
    
    @Column(length = 20)
    private String status; // APROVADO, REPROVADO, EM_ANDAMENTO, PENDENTE
    
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
    public CareerStage getCareerStage() { return careerStage; }
    public void setCareerStage(CareerStage careerStage) { this.careerStage = careerStage; }
    public LocalDate getEvaluationDate() { return evaluationDate; }
    public void setEvaluationDate(LocalDate evaluationDate) { this.evaluationDate = evaluationDate; }
    public String getEvaluationType() { return evaluationType; }
    public void setEvaluationType(String evaluationType) { this.evaluationType = evaluationType; }
    public BigDecimal getTotalScore() { return totalScore; }
    public void setTotalScore(BigDecimal totalScore) { this.totalScore = totalScore; }
    public BigDecimal getCompetencyScore() { return competencyScore; }
    public void setCompetencyScore(BigDecimal competencyScore) { this.competencyScore = competencyScore; }
    public BigDecimal getEfficiencyScore() { return efficiencyScore; }
    public void setEfficiencyScore(BigDecimal efficiencyScore) { this.efficiencyScore = efficiencyScore; }
    public BigDecimal getDedicationScore() { return dedicationScore; }
    public void setDedicationScore(BigDecimal dedicationScore) { this.dedicationScore = dedicationScore; }
    public BigDecimal getCulturalScore() { return culturalScore; }
    public void setCulturalScore(BigDecimal culturalScore) { this.culturalScore = culturalScore; }
    public BigDecimal getEthicalScore() { return ethicalScore; }
    public void setEthicalScore(BigDecimal ethicalScore) { this.ethicalScore = ethicalScore; }
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
