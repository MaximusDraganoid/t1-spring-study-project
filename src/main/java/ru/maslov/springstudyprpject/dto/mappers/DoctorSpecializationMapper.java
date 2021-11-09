package ru.maslov.springstudyprpject.dto.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import ru.maslov.springstudyprpject.dto.DoctorsSpecializationDTO;
import ru.maslov.springstudyprpject.entities.DoctorsSpecialization;
import ru.maslov.springstudyprpject.servicies.TypeOfAppointmentService;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring")
public abstract class DoctorSpecializationMapper {

    @Autowired
    protected TypeOfAppointmentService service;

    public abstract DoctorsSpecializationDTO toDoctorsSpecializationDTO(
            DoctorsSpecialization doctorsSpecialization);

    @Mapping(target = "typeOfAppointmentSet",
            expression = "java(service.getTypesBySpecializationId(doctorsSpecializationDTO.getId()))")
    public abstract DoctorsSpecialization toDoctorsSpecialization(
            DoctorsSpecializationDTO doctorsSpecializationDTO);
}
