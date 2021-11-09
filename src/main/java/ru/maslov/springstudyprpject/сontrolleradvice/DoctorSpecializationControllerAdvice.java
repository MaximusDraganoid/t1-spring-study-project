package ru.maslov.springstudyprpject.сontrolleradvice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.maslov.springstudyprpject.errors.ApiError;
import ru.maslov.springstudyprpject.exceptions.DoctorSpecializationNotFoundException;

@RestControllerAdvice
public class DoctorSpecializationControllerAdvice {
    @ExceptionHandler({DoctorSpecializationNotFoundException.class})
    public ResponseEntity<ApiError> handleDoctorSpecializationNotFound(DoctorSpecializationNotFoundException e) {
        ApiError apiError = new ApiError("doctors specialization not found", e.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }
}
