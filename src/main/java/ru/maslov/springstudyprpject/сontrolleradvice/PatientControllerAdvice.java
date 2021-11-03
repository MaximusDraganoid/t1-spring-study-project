package ru.maslov.springstudyprpject.сontrolleradvice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.maslov.springstudyprpject.errors.ApiError;
import ru.maslov.springstudyprpject.exceptions.PatientDataValidationException;
import ru.maslov.springstudyprpject.exceptions.PatientNotFoundException;

@RestControllerAdvice
public class PatientControllerAdvice {

    @ExceptionHandler({PatientNotFoundException.class})
    public ResponseEntity<ApiError> handlePatientNotFoundException(PatientNotFoundException e) {
        ApiError apiError = new ApiError("patient doesn't found", e.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({PatientDataValidationException.class})
    public ResponseEntity<ApiError>
        handlePatientDataValidationException(PatientDataValidationException e) {
        ApiError apiError = new ApiError("patient validation data exception",
                e.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
