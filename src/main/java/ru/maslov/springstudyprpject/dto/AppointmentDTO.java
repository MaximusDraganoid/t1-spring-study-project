package ru.maslov.springstudyprpject.dto;

import ru.maslov.springstudyprpject.entities.StatusOfAppointment;
import ru.maslov.springstudyprpject.entities.TypeOfAppointment;

import java.time.LocalDateTime;

public class AppointmentDTO {
    private Long id;
    private DoctorDTO doctor;
    private PatientDTO patient;
    private LocalDateTime startAppointmentDateTime;
    private TypeOfAppointment typeOfAppointment;
    private StatusOfAppointment status;
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DoctorDTO getDoctor() {
        return doctor;
    }

    public void setDoctor(DoctorDTO doctor) {
        this.doctor = doctor;
    }

    public PatientDTO getPatient() {
        return patient;
    }

    public void setPatient(PatientDTO patient) {
        this.patient = patient;
    }

    public LocalDateTime getStartAppointmentDateTime() {
        return startAppointmentDateTime;
    }

    public void setStartAppointmentDateTime(LocalDateTime startAppointmentDateTime) {
        this.startAppointmentDateTime = startAppointmentDateTime;
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
