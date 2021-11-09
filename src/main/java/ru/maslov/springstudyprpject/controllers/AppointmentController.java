package ru.maslov.springstudyprpject.controllers;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.maslov.springstudyprpject.dto.AppointmentDTO;
import ru.maslov.springstudyprpject.dto.mappers.AppointmentMapper;
import ru.maslov.springstudyprpject.entities.Appointment;
import ru.maslov.springstudyprpject.servicies.AppointmentService;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping(path = "appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;

    private final AppointmentMapper mapper;

    public AppointmentController(AppointmentService appointmentService,
                                 AppointmentMapper mapper) {
        this.appointmentService = appointmentService;
        this.mapper = mapper;
    }

    @GetMapping
    public List<AppointmentDTO> getAppointmentBySpecializationAndData(
            @RequestParam("spec_id") @NotNull Long specializationId,
            @RequestParam("data") @NotNull String date,
            @RequestParam("appointment_type_id") @NotNull Long appointmentTypeId
            ) {
        Set<Appointment> appointments = appointmentService.getAppointmentBySpecializationIdAndData(specializationId, date, appointmentTypeId);

        return appointments
                .stream()
                .map(mapper::toAppointmentDTO)
                .collect(Collectors.toList());
    }

}
