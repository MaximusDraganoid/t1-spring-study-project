package ru.maslov.springstudyprpject.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.maslov.springstudyprpject.entities.Doctor;
import ru.maslov.springstudyprpject.errors.ApiError;
import ru.maslov.springstudyprpject.servicies.DoctorService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
@RestController
@RequestMapping(path = "doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping
    public List<Doctor> getDoctorsList() {
        return doctorService.getList();
    }

    @GetMapping(path = "/{id}")
    public Doctor getDoctorById(@PathVariable("id") @NotNull Long id) {
        return doctorService.getById(id);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteDoctorById(@PathVariable("id") @NotNull Long id) {
        doctorService.deleteById(id);
    }

    @PostMapping
    public Doctor createDoctor(@RequestBody @Valid Doctor doctor) {
        return doctorService.create(doctor);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ApiError>
        handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ApiError apiError = new ApiError("Validation Error", e.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}