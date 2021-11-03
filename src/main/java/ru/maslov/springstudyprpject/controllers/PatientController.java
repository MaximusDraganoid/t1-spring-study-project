package ru.maslov.springstudyprpject.controllers;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.maslov.springstudyprpject.entities.Patient;
import ru.maslov.springstudyprpject.servicies.PatientService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
@RestController
@RequestMapping(path = "patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public List<Patient> getPatients() {
        return patientService.getPatients();
    }

    @GetMapping(path = "/{id}")
    public Patient getById(@PathVariable("id") @NotNull Long id) {
        return patientService.getById(id);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteById(@PathVariable("id") @NotNull Long id) {
        patientService.deleteById(id);
    }

    @PostMapping
    public Patient create(@RequestBody @Valid Patient patient) {
        return patientService.create(patient);
    }

}
