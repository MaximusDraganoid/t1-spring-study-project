package ru.maslov.springstudyprpject.dto.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.maslov.springstudyprpject.dto.AppointmentDTO;
import ru.maslov.springstudyprpject.entities.Appointment;

@Mapper(componentModel = "spring",
        uses = {DoctorMapper.class,
                DoctorSpecializationMapper.class,
                PatientMapper.class})
public abstract class AppointmentMapper {
    @Mapping(target = "dataTimeOfAppointment", source = "startAppointmentDateTime")
    public abstract Appointment toAppointment(AppointmentDTO appointmentDTO);
    @Mapping(target = "startAppointmentDateTime", source = "dataTimeOfAppointment")
    public abstract AppointmentDTO toAppointmentDTO(Appointment appointment);

}
