package com.pge.people.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "diversity_indicator")
public class DiversityIndicator {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Integer referenceYear;
    
    @Column(nullable = false)
    private Integer referenceMonth; // 1-12
    
    @Column(nullable = false)
    private Integer totalEmployees;
    
    @Column
    private Integer maleCount;
    
    @Column
    private Integer femaleCount;
    
    @Column
    private Integer nonBinaryCount;
    
    @Column
    private Integer whiteCount; // Raça branca
    
    @Column
    private Integer blackCount; // Raça preta
    
    @Column
    private Integer brownCount; // Raça parda
    
    @Column
    private Integer yellowCount; // Raça amarela
    
    @Column
    private Integer indigenousCount; // Raça indígena
    
    @Column
    private Integer pcdCount; // Pessoas com deficiência
    
    @Column
    private Integer lgbtqiaCount;
    
    @Column
    private Integer ageRange18to25;
    
    @Column
    private Integer ageRange26to35;
    
    @Column
    private Integer ageRange36to45;
    
    @Column
    private Integer ageRange46to55;
    
    @Column
    private Integer ageRange56plus;
    
    @Column(length = 2000)
    private String observations;
    
    @Column(length = 100)
    private String preparedBy;
    
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
    public Integer getReferenceYear() { return referenceYear; }
    public void setReferenceYear(Integer referenceYear) { this.referenceYear = referenceYear; }
    public Integer getReferenceMonth() { return referenceMonth; }
    public void setReferenceMonth(Integer referenceMonth) { this.referenceMonth = referenceMonth; }
    public Integer getTotalEmployees() { return totalEmployees; }
    public void setTotalEmployees(Integer totalEmployees) { this.totalEmployees = totalEmployees; }
    public Integer getMaleCount() { return maleCount; }
    public void setMaleCount(Integer maleCount) { this.maleCount = maleCount; }
    public Integer getFemaleCount() { return femaleCount; }
    public void setFemaleCount(Integer femaleCount) { this.femaleCount = femaleCount; }
    public Integer getNonBinaryCount() { return nonBinaryCount; }
    public void setNonBinaryCount(Integer nonBinaryCount) { this.nonBinaryCount = nonBinaryCount; }
    public Integer getWhiteCount() { return whiteCount; }
    public void setWhiteCount(Integer whiteCount) { this.whiteCount = whiteCount; }
    public Integer getBlackCount() { return blackCount; }
    public void setBlackCount(Integer blackCount) { this.blackCount = blackCount; }
    public Integer getBrownCount() { return brownCount; }
    public void setBrownCount(Integer brownCount) { this.brownCount = brownCount; }
    public Integer getYellowCount() { return yellowCount; }
    public void setYellowCount(Integer yellowCount) { this.yellowCount = yellowCount; }
    public Integer getIndigenousCount() { return indigenousCount; }
    public void setIndigenousCount(Integer indigenousCount) { this.indigenousCount = indigenousCount; }
    public Integer getPcdCount() { return pcdCount; }
    public void setPcdCount(Integer pcdCount) { this.pcdCount = pcdCount; }
    public Integer getLgbtqiaCount() { return lgbtqiaCount; }
    public void setLgbtqiaCount(Integer lgbtqiaCount) { this.lgbtqiaCount = lgbtqiaCount; }
    public Integer getAgeRange18to25() { return ageRange18to25; }
    public void setAgeRange18to25(Integer ageRange18to25) { this.ageRange18to25 = ageRange18to25; }
    public Integer getAgeRange26to35() { return ageRange26to35; }
    public void setAgeRange26to35(Integer ageRange26to35) { this.ageRange26to35 = ageRange26to35; }
    public Integer getAgeRange36to45() { return ageRange36to45; }
    public void setAgeRange36to45(Integer ageRange36to45) { this.ageRange36to45 = ageRange36to45; }
    public Integer getAgeRange46to55() { return ageRange46to55; }
    public void setAgeRange46to55(Integer ageRange46to55) { this.ageRange46to55 = ageRange46to55; }
    public Integer getAgeRange56plus() { return ageRange56plus; }
    public void setAgeRange56plus(Integer ageRange56plus) { this.ageRange56plus = ageRange56plus; }
    public String getObservations() { return observations; }
    public void setObservations(String observations) { this.observations = observations; }
    public String getPreparedBy() { return preparedBy; }
    public void setPreparedBy(String preparedBy) { this.preparedBy = preparedBy; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
    public LocalDate getCreatedAt() { return createdAt; }
}
