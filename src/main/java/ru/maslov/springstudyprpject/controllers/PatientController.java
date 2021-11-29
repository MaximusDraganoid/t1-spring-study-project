package ru.maslov.springstudyprpject.controllers;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.maslov.springstudyprpject.dto.PatientDTO;
import ru.maslov.springstudyprpject.dto.mappers.PatientMapper;
import ru.maslov.springstudyprpject.entities.Patient;
import ru.maslov.springstudyprpject.servicies.PatientService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping(path = "/patients")
public class PatientController {

    private final PatientMapper mapper;

    private final PatientService patientService;

    public PatientController(PatientMapper mapper, PatientService patientService) {
        this.mapper = mapper;
        this.patientService = patientService;
    }

    //todo: убрать в последствии - использутеся только для генерации тестовых данныъ
    @GetMapping(path = "/test")
    public List<PatientDTO> getPatientDTOs() {
        return patientService.getPatients()
                .stream()
                .map(mapper::toPatientDTO)
                .collect(Collectors.toList());
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

    @PutMapping
    public Patient changePatientData(@RequestBody @NotNull Patient patient) {
        return patientService.changePatientData(patient);
    }

    @PostMapping
    public Patient create(@RequestBody @NotNull @Valid Patient patient) {
        return patientService.save(patient);
    }
}
