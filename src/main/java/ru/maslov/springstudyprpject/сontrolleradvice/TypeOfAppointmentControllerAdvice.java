package ru.maslov.springstudyprpject.сontrolleradvice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.maslov.springstudyprpject.errors.ApiError;
import ru.maslov.springstudyprpject.exceptions.TypeOfAppointmentNotFoundException;

@RestControllerAdvice
public class TypeOfAppointmentControllerAdvice {
    @ExceptionHandler({TypeOfAppointmentNotFoundException.class})
    public ResponseEntity<ApiError> handleTypeOfAppointmentNotFoundException(TypeOfAppointmentNotFoundException e) {
        ApiError apiError = new ApiError("type of appointment not found", e.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }
}
