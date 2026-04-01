package com.pge.people.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "employee")
public class Employee {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 200)
    private String name;
    
    @Column(unique = true, nullable = false, length = 20)
    private String registrationNumber; // Matrícula
    
    @Column(unique = true, length = 15)
    private String cpf;
    
    @Column(length = 100)
    private String email;
    
    @Column(length = 20)
    private String phone;
    
    @Column(nullable = false, length = 50)
    private String position; // Procurador, Servidor Efetivo, Comissionado, Estagiário
    
    @Column(length = 50)
    private String specialty; // Especialidade (para procuradores)
    
    @Column(nullable = false)
    private LocalDate admissionDate;
    
    @Column
    private LocalDate probationEndDate; // Fim do estágio probatório
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizational_unit_id")
    private OrganizationalUnit organizationalUnit; // Lotação atual
    
    @Column(length = 20)
    private String employmentStatus; // ATIVO, LICENCA, AFASTADO, EXONERADO, APOSENTADO
    
    @Column(length = 20)
    private String gender;
    
    @Column(length = 30)
    private String race; // BRANCA, PRETA, PARDA, AMARELA, INDIGENA
    
    @Column
    private Boolean isPcd = false; // Pessoa com deficiência
    
    @Column
    private LocalDate birthDate;
    
    @Column(length = 200)
    private String address;
    
    @Column(length = 100)
    private String city;
    
    @Column(length = 2)
    private String state;
    
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private Set<EmployeeCareer> careerHistory = new HashSet<>();
    
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private Set<TrainingEnrollment> trainingEnrollments = new HashSet<>();
    
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private Set<PerformanceEvaluation> performanceEvaluations = new HashSet<>();
    
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private Set<EmployeeBenefit> benefits = new HashSet<>();
    
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
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getRegistrationNumber() { return registrationNumber; }
    public void setRegistrationNumber(String registrationNumber) { this.registrationNumber = registrationNumber; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }
    public String getSpecialty() { return specialty; }
    public void setSpecialty(String specialty) { this.specialty = specialty; }
    public LocalDate getAdmissionDate() { return admissionDate; }
    public void setAdmissionDate(LocalDate admissionDate) { this.admissionDate = admissionDate; }
    public LocalDate getProbationEndDate() { return probationEndDate; }
    public void setProbationEndDate(LocalDate probationEndDate) { this.probationEndDate = probationEndDate; }
    public OrganizationalUnit getOrganizationalUnit() { return organizationalUnit; }
    public void setOrganizationalUnit(OrganizationalUnit organizationalUnit) { this.organizationalUnit = organizationalUnit; }
    public String getEmploymentStatus() { return employmentStatus; }
    public void setEmploymentStatus(String employmentStatus) { this.employmentStatus = employmentStatus; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getRace() { return race; }
    public void setRace(String race) { this.race = race; }
    public Boolean getPcd() { return isPcd; }
    public void setPcd(Boolean pcd) { isPcd = pcd; }
    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
    public Set<EmployeeCareer> getCareerHistory() { return careerHistory; }
    public void setCareerHistory(Set<EmployeeCareer> careerHistory) { this.careerHistory = careerHistory; }
    public Set<TrainingEnrollment> getTrainingEnrollments() { return trainingEnrollments; }
    public void setTrainingEnrollments(Set<TrainingEnrollment> trainingEnrollments) { this.trainingEnrollments = trainingEnrollments; }
    public Set<PerformanceEvaluation> getPerformanceEvaluations() { return performanceEvaluations; }
    public void setPerformanceEvaluations(Set<PerformanceEvaluation> performanceEvaluations) { this.performanceEvaluations = performanceEvaluations; }
    public Set<EmployeeBenefit> getBenefits() { return benefits; }
    public void setBenefits(Set<EmployeeBenefit> benefits) { this.benefits = benefits; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
    public LocalDate getCreatedAt() { return createdAt; }
}
