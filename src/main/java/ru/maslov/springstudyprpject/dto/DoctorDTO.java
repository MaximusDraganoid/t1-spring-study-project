package ru.maslov.springstudyprpject.dto;

public class DoctorDTO extends UserDTO {
    private DoctorsSpecializationDTO specialization;

    public DoctorsSpecializationDTO getSpecialization() {
        return specialization;
    }

    public void setSpecialization(DoctorsSpecializationDTO specialization) {
        this.specialization = specialization;
    }
}

