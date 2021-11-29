package ru.maslov.springstudyprpject.controllers;

import org.springframework.web.bind.annotation.*;
import ru.maslov.springstudyprpject.entities.DoctorsSpecialization;
import ru.maslov.springstudyprpject.servicies.DoctorsSpecializationService;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(path = "/specializations")
public class DoctorSpecializationController {

    private final DoctorsSpecializationService doctorsSpecializationService;

    public DoctorSpecializationController(DoctorsSpecializationService doctorsSpecializationService) {
        this.doctorsSpecializationService = doctorsSpecializationService;
    }

    @GetMapping
    public List<DoctorsSpecialization> getSpecializations() {
        return doctorsSpecializationService.getAllSpecializations();
    }

    @PostMapping
    public DoctorsSpecialization saveSpecialization(@RequestBody @NotNull DoctorsSpecialization specialization) {
        //todo:
        return doctorsSpecializationService.saveSpecialization(specialization);
    }
}
