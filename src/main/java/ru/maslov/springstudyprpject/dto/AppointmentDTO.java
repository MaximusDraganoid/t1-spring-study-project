package ru.maslov.springstudyprpject.dto;

import ru.maslov.springstudyprpject.entities.StatusOfAppointment;
import ru.maslov.springstudyprpject.entities.TypeOfAppointment;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class AppointmentDTO {

    private Long id;

    @NotNull
    private DoctorDTO doctor;

    @NotNull
    private PatientDTO patient;

    @NotNull
    private LocalDateTime startAppointmentDateTime;

    @NotNull
    private TypeOfAppointment typeOfAppointment;

    @NotNull
    private StatusOfAppointment statusOfAppointment;

    private String description;

    public AppointmentDTO() {}

    public AppointmentDTO(Long id,
                          @NotNull DoctorDTO doctor,
                          @NotNull PatientDTO patient,
                          @NotNull LocalDateTime startAppointmentDateTime,
                          @NotNull TypeOfAppointment typeOfAppointment,
                          @NotNull StatusOfAppointment statusOfAppointment,
                          String description) {
        this.id = id;
        this.doctor = doctor;
        this.patient = patient;
        this.startAppointmentDateTime = startAppointmentDateTime;
        this.typeOfAppointment = typeOfAppointment;
        this.statusOfAppointment = statusOfAppointment;
        this.description = description;
    }

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
}
