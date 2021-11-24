package ru.maslov.springstudyprpject.entities;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

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
    private StatusOfAppointment statusOfAppointment;

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
        this.statusOfAppointment = status;
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
        return statusOfAppointment;
    }

    public void setStatus(StatusOfAppointment status) {
        this.statusOfAppointment = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Appointment that = (Appointment) o;

        if (!Objects.equals(patient, that.patient)) return false;
        if (!Objects.equals(doctor, that.doctor)) return false;
        if (!Objects.equals(dataTimeOfAppointment, that.dataTimeOfAppointment))
            return false;
        if (!Objects.equals(typeOfAppointment, that.typeOfAppointment))
            return false;
        if (statusOfAppointment != that.statusOfAppointment) return false;
        return Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        int result = patient != null ? patient.hashCode() : 0;
        result = 31 * result + (doctor != null ? doctor.hashCode() : 0);
        result = 31 * result + (dataTimeOfAppointment != null ? dataTimeOfAppointment.hashCode() : 0);
        result = 31 * result + (typeOfAppointment != null ? typeOfAppointment.hashCode() : 0);
        result = 31 * result + (statusOfAppointment != null ? statusOfAppointment.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
