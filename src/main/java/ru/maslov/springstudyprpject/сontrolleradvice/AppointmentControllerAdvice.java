package ru.maslov.springstudyprpject.сontrolleradvice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.maslov.springstudyprpject.errors.ApiError;
import ru.maslov.springstudyprpject.exceptions.AppointmentDateFormatException;

@RestControllerAdvice
public class AppointmentControllerAdvice {
    @ExceptionHandler({AppointmentDateFormatException.class})
    public ResponseEntity<ApiError> handleAppointmentDateFormatException(AppointmentDateFormatException e) {
        ApiError apiError = new ApiError("Data format exception", e.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
