package ru.maslov.springstudyprpject.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.maslov.springstudyprpject.entities.DoctorsSpecialization;
import ru.maslov.springstudyprpject.servicies.DoctorsSpecializationService;

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
    public DoctorsSpecialization saveSpecialization(DoctorsSpecialization specialization) {
        //todo:
        return doctorsSpecializationService.saveSpecialization(specialization);
    }
}
