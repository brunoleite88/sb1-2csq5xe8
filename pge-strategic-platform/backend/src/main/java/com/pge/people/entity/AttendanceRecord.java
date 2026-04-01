package com.pge.people.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "attendance_record")
public class AttendanceRecord {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;
    
    @Column(nullable = false)
    private LocalDate recordDate;
    
    @Column
    private LocalTime checkIn;
    
    @Column
    private LocalTime checkOut;
    
    @Column
    private LocalTime lunchStart;
    
    @Column
    private LocalTime lunchEnd;
    
    @Column(precision = 5, scale = 2)
    private Float workedHours; // Horas trabalhadas no dia
    
    @Column(length = 20)
    private String recordType; // PRESENCIAL, TELETRABALHO, EXTERNO, FERIADO, FERIAS, LICENCA
    
    @Column(length = 20)
    private String status; // NORMAL, ATRASO, FALTA, JUSTIFICADO
    
    @Column(length = 500)
    private String justification;
    
    @Column(length = 100)
    private String approvedBy;
    
    @Column
    private Boolean approved = false;
    
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
    public LocalDate getRecordDate() { return recordDate; }
    public void setRecordDate(LocalDate recordDate) { this.recordDate = recordDate; }
    public LocalTime getCheckIn() { return checkIn; }
    public void setCheckIn(LocalTime checkIn) { this.checkIn = checkIn; }
    public LocalTime getCheckOut() { return checkOut; }
    public void setCheckOut(LocalTime checkOut) { this.checkOut = checkOut; }
    public LocalTime getLunchStart() { return lunchStart; }
    public void setLunchStart(LocalTime lunchStart) { this.lunchStart = lunchStart; }
    public LocalTime getLunchEnd() { return lunchEnd; }
    public void setLunchEnd(LocalTime lunchEnd) { this.lunchEnd = lunchEnd; }
    public Float getWorkedHours() { return workedHours; }
    public void setWorkedHours(Float workedHours) { this.workedHours = workedHours; }
    public String getRecordType() { return recordType; }
    public void setRecordType(String recordType) { this.recordType = recordType; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getJustification() { return justification; }
    public void setJustification(String justification) { this.justification = justification; }
    public String getApprovedBy() { return approvedBy; }
    public void setApprovedBy(String approvedBy) { this.approvedBy = approvedBy; }
    public Boolean getApproved() { return approved; }
    public void setApproved(Boolean approved) { this.approved = approved; }
    public String getObservations() { return observations; }
    public void setObservations(String observations) { this.observations = observations; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
    public LocalDate getCreatedAt() { return createdAt; }
}
