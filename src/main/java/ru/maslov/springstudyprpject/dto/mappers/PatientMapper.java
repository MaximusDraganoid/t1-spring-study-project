package ru.maslov.springstudyprpject.dto.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import ru.maslov.springstudyprpject.dto.PatientDTO;
import ru.maslov.springstudyprpject.entities.Patient;
import ru.maslov.springstudyprpject.servicies.AppointmentService;
import ru.maslov.springstudyprpject.servicies.PatientService;

@Mapper(componentModel = "spring")
public abstract class PatientMapper {

    @Autowired
    protected AppointmentService service;

    @Autowired
    protected PatientService patientService;

    public abstract PatientDTO toPatientDTO(Patient patient);

    @Mappings(
            {@Mapping(target = "appointmentSet",
                    expression = "java(service.findAppointmentByPatientId(patientDTO.getId()))"),
            @Mapping(target = "login",
                    expression = "java(patientService.getById(patientDTO.getId()).getLogin())"),
            @Mapping(target = "password",
                    expression = "java(patientService.getById(patientDTO.getId()).getPassword())"),
            @Mapping(target = "phoneNumber",
                    expression = "java(patientService.getById(patientDTO.getId()).getPhoneNumber())")
            }
    )
    public abstract Patient toPatient(PatientDTO patientDTO);
}
