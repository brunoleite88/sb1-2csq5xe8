package com.pge.people.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.math.BigDecimal;

@Entity
@Table(name = "employee_benefit")
public class EmployeeBenefit {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;
    
    @Column(nullable = false, length = 50)
    private String benefitType; // GRATIFICACAO_CIENTIFICA, AUXILIO_SAUDE, AUXILIO_ALIMENTACAO, AUXILIO_TRANSPORTE, DIARIAS, AJUDA_CUSTO
    
    @Column(length = 200)
    private String description;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal value;
    
    @Column(nullable = false)
    private LocalDate startDate;
    
    @Column
    private LocalDate endDate;
    
    @Column(length = 20)
    private String status; // ATIVO, SUSPENSO, CANCELADO, ENCERRADO
    
    @Column(length = 500)
    private String justification;
    
    @Column(length = 100)
    private String approvalAct; // Número do ato de aprovação
    
    @Column
    private Boolean requiresRenewal = false;
    
    @Column
    private LocalDate renewalDate;
    
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
    public String getBenefitType() { return benefitType; }
    public void setBenefitType(String benefitType) { this.benefitType = benefitType; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public BigDecimal getValue() { return value; }
    public void setValue(BigDecimal value) { this.value = value; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getJustification() { return justification; }
    public void setJustification(String justification) { this.justification = justification; }
    public String getApprovalAct() { return approvalAct; }
    public void setApprovalAct(String approvalAct) { this.approvalAct = approvalAct; }
    public Boolean getRequiresRenewal() { return requiresRenewal; }
    public void setRequiresRenewal(Boolean requiresRenewal) { this.requiresRenewal = requiresRenewal; }
    public LocalDate getRenewalDate() { return renewalDate; }
    public void setRenewalDate(LocalDate renewalDate) { this.renewalDate = renewalDate; }
    public String getObservations() { return observations; }
    public void setObservations(String observations) { this.observations = observations; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
    public LocalDate getCreatedAt() { return createdAt; }
}
