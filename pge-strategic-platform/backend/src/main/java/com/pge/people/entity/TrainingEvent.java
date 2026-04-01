package com.pge.people.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "training_event")
public class TrainingEvent {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 200)
    private String title;
    
    @Column(length = 1000)
    private String description;
    
    @Column(nullable = false, length = 50)
    private String category; // CURSO, WORKSHOP, SEMINÁRIO, PALESTRA, RESIDÊNCIA, PÓS_GRADUAÇÃO
    
    @Column(nullable = false)
    private LocalDate startDate;
    
    @Column(nullable = false)
    private LocalDate endDate;
    
    @Column(nullable = false)
    private Integer workloadHours; // Carga horária em horas
    
    @Column(length = 100)
    private String modality; // PRESENCIAL, ONLINE, HIBRIDO
    
    @Column(length = 200)
    private String location;
    
    @Column(length = 100)
    private String instructor;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizational_unit_id")
    private OrganizationalUnit organizingUnit; // ESAP, parceiro, etc.
    
    @Column(nullable = false)
    private Integer maxParticipants;
    
    @Column
    private Integer enrolledCount = 0;
    
    @Column
    private Boolean certificateEnabled = true;
    
    @Column(length = 20)
    private String status; // ABERTO, EM_ANDAMENTO, CONCLUIDO, CANCELADO
    
    @Column(length = 200)
    private String partnershipInfo; // Convênio (UFMA, UEMA, FAPEMA, PUC-RS)
    
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
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    public Integer getWorkloadHours() { return workloadHours; }
    public void setWorkloadHours(Integer workloadHours) { this.workloadHours = workloadHours; }
    public String getModality() { return modality; }
    public void setModality(String modality) { this.modality = modality; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getInstructor() { return instructor; }
    public void setInstructor(String instructor) { this.instructor = instructor; }
    public OrganizationalUnit getOrganizingUnit() { return organizingUnit; }
    public void setOrganizingUnit(OrganizationalUnit organizingUnit) { this.organizingUnit = organizingUnit; }
    public Integer getMaxParticipants() { return maxParticipants; }
    public void setMaxParticipants(Integer maxParticipants) { this.maxParticipants = maxParticipants; }
    public Integer getEnrolledCount() { return enrolledCount; }
    public void setEnrolledCount(Integer enrolledCount) { this.enrolledCount = enrolledCount; }
    public Boolean getCertificateEnabled() { return certificateEnabled; }
    public void setCertificateEnabled(Boolean certificateEnabled) { this.certificateEnabled = certificateEnabled; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getPartnershipInfo() { return partnershipInfo; }
    public void setPartnershipInfo(String partnershipInfo) { this.partnershipInfo = partnershipInfo; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
    public LocalDate getCreatedAt() { return createdAt; }
}
