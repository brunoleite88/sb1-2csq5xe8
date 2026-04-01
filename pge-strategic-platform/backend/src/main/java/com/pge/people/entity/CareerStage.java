package com.pge.people.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "career_stage")
public class CareerStage {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String name; // Estágio Probatório, Classe Inicial, etc.
    
    @Column(length = 500)
    private String description;
    
    @Column(nullable = false)
    private Integer orderLevel; // Ordem hierárquica
    
    @Column(nullable = false)
    private Integer durationMonths; // Duração em meses (ex: 36 para probatório)
    
    @Column(nullable = false)
    private Boolean active = true;
    
    @OneToMany(mappedBy = "careerStage", cascade = CascadeType.ALL)
    private Set<CareerEvaluation> evaluations = new HashSet<>();
    
    @OneToMany(mappedBy = "careerStage", cascade = CascadeType.ALL)
    private Set<EmployeeCareer> employeeCareers = new HashSet<>();
    
    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Integer getOrderLevel() { return orderLevel; }
    public void setOrderLevel(Integer orderLevel) { this.orderLevel = orderLevel; }
    public Integer getDurationMonths() { return durationMonths; }
    public void setDurationMonths(Integer durationMonths) { this.durationMonths = durationMonths; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
    public Set<CareerEvaluation> getEvaluations() { return evaluations; }
    public void setEvaluations(Set<CareerEvaluation> evaluations) { this.evaluations = evaluations; }
    public Set<EmployeeCareer> getEmployeeCareers() { return employeeCareers; }
    public void setEmployeeCareers(Set<EmployeeCareer> employeeCareers) { this.employeeCareers = employeeCareers; }
}
