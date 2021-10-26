package ru.maslov.springstudyprpject.entities;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Patient patient;

    @ManyToOne
    private Doctor doctor;

    private LocalDateTime dataTimeOfAppointment;

    @OneToOne
    private TypeOfAppointment typeOfAppointment;

    @Enumerated(EnumType.STRING)
    private StatusOfAppointment status;

    private String description;

    public Appointment() {}

    public Appointment(Patient patient,
                       Doctor doctor,
                       LocalDateTime dataTimeOfAppointment,
                       TypeOfAppointment typeOfAppointment,
                       StatusOfAppointment status,
                       String description) {
        this.patient = patient;
        this.doctor = doctor;
        this.dataTimeOfAppointment = dataTimeOfAppointment;
        this.typeOfAppointment = typeOfAppointment;
        this.status = status;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public LocalDateTime getDataTimeOfAppointment() {
        return dataTimeOfAppointment;
    }

    public void setDataTimeOfAppointment(LocalDateTime dataTimeOfAppointment) {
        this.dataTimeOfAppointment = dataTimeOfAppointment;
    }

    public TypeOfAppointment getTypeOfAppointment() {
        return typeOfAppointment;
    }

    public void setTypeOfAppointment(TypeOfAppointment typeOfAppointment) {
        this.typeOfAppointment = typeOfAppointment;
    }

    public StatusOfAppointment getStatus() {
        return status;
    }

    public void setStatus(StatusOfAppointment status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
