package ru.maslov.springstudyprpject.dto.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.maslov.springstudyprpject.dto.AppointmentDTO;
import ru.maslov.springstudyprpject.entities.Appointment;
import ru.maslov.springstudyprpject.entities.StatusOfAppointment;

@Mapper(componentModel = "spring",
        uses = {DoctorMapper.class,
                DoctorSpecializationMapper.class,
                PatientMapper.class })
public abstract class AppointmentMapper {
    @Mappings({
            @Mapping(target = "dataTimeOfAppointment", source = "startAppointmentDateTime"),
//            @Mapping(target = "statusOfAppointment",
//                    expression = "java(getByValue(appointmentDTO.getStatusOfAppointment()))")
    })
    public abstract Appointment toAppointment(AppointmentDTO appointmentDTO);

    @Mappings({
            @Mapping(target = "startAppointmentDateTime", source = "dataTimeOfAppointment"),
            @Mapping(target = "statusOfAppointment",
                    expression = "java(appointment.getStatusOfAppointment().ordinal())")
    })
    public abstract AppointmentDTO toAppointmentDTO(Appointment appointment);

    public StatusOfAppointment getByValue(int value) {
        return StatusOfAppointment.getByValue(value);
    }
}
