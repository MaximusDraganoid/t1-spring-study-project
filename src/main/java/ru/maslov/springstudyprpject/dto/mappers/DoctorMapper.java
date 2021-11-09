package ru.maslov.springstudyprpject.dto.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import ru.maslov.springstudyprpject.dto.DoctorDTO;
import ru.maslov.springstudyprpject.entities.Doctor;
import ru.maslov.springstudyprpject.servicies.DoctorService;

@Mapper(componentModel = "spring")
public abstract class DoctorMapper {

    @Autowired
    protected DoctorService doctorService;

    public abstract DoctorDTO doctorDTO(Doctor doctor);

    @Mappings({
            @Mapping(target = "login",
                    expression = "java(doctorService.getById(doctorDTO.getId()).getLogin())"),
            @Mapping(target = "password",
                    expression = "java(doctorService.getById(doctorDTO.getId()).getPassword())"),
            @Mapping(target = "phoneNumber",
                    expression = "java(doctorService.getById(doctorDTO.getId()).getPhoneNumber())"),
            @Mapping(target = "scheduleList",
                    expression = "java(doctorService.getById(doctorDTO.getId()).getScheduleList())"),
            @Mapping(target = "appointments",
                    expression = "java(doctorService.getById(doctorDTO.getId()).getAppointments())")
    })
    public abstract Doctor toDoctor(DoctorDTO doctorDTO);
}
