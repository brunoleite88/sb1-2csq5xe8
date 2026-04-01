package com.pge.people.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "training_enrollment")
public class TrainingEnrollment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "training_event_id", nullable = false)
    private TrainingEvent trainingEvent;
    
    @Column(nullable = false)
    private LocalDate enrollmentDate;
    
    @Column(length = 20)
    private String status; // INSCRITO, APROVADO, REPROVADO, CANCELADO, CONCLUIDO
    
    @Column
    private Boolean attended = false;
    
    @Column
    private Integer attendancePercentage;
    
    @Column
    private BigDecimal finalScore;
    
    @Column(length = 200)
    private String certificateCode; // Código do certificado
    
    @Column
    private Boolean certificateIssued = false;
    
    @Column
    private LocalDate certificateIssueDate;
    
    @Column(length = 500)
    private String observations;
    
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
    public TrainingEvent getTrainingEvent() { return trainingEvent; }
    public void setTrainingEvent(TrainingEvent trainingEvent) { this.trainingEvent = trainingEvent; }
    public LocalDate getEnrollmentDate() { return enrollmentDate; }
    public void setEnrollmentDate(LocalDate enrollmentDate) { this.enrollmentDate = enrollmentDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Boolean getAttended() { return attended; }
    public void setAttended(Boolean attended) { this.attended = attended; }
    public Integer getAttendancePercentage() { return attendancePercentage; }
    public void setAttendancePercentage(Integer attendancePercentage) { this.attendancePercentage = attendancePercentage; }
    public BigDecimal getFinalScore() { return finalScore; }
    public void setFinalScore(BigDecimal finalScore) { this.finalScore = finalScore; }
    public String getCertificateCode() { return certificateCode; }
    public void setCertificateCode(String certificateCode) { this.certificateCode = certificateCode; }
    public Boolean getCertificateIssued() { return certificateIssued; }
    public void setCertificateIssued(Boolean certificateIssued) { this.certificateIssued = certificateIssued; }
    public LocalDate getCertificateIssueDate() { return certificateIssueDate; }
    public void setCertificateIssueDate(LocalDate certificateIssueDate) { this.certificateIssueDate = certificateIssueDate; }
    public String getObservations() { return observations; }
    public void setObservations(String observations) { this.observations = observations; }
    public LocalDate getCreatedAt() { return createdAt; }
}
