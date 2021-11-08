package ru.maslov.springstudyprpject.controllers;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.maslov.springstudyprpject.entities.Appointment;
import ru.maslov.springstudyprpject.servicies.AppointmentService;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@Validated
@RestController
@RequestMapping(path = "appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping
    public Set<Appointment> getAppointmentBySpecializationAndData(
            @RequestParam("spec_id") @NotNull Long specializationId,
            @RequestParam("data") @NotNull String date,
            @RequestParam("appointment_type_id") @NotNull Long appointmentTypeId
            ) {
        return appointmentService.getAppointmentBySpecializationIdAndData(specializationId, date, appointmentTypeId);
    }

}
