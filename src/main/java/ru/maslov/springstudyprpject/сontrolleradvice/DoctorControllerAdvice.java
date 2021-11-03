package ru.maslov.springstudyprpject.сontrolleradvice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.maslov.springstudyprpject.errors.ApiError;
import ru.maslov.springstudyprpject.exceptions.DoctorDataValidationException;
import ru.maslov.springstudyprpject.exceptions.DoctorNotFoundException;

@RestControllerAdvice
public class DoctorControllerAdvice {

    @ExceptionHandler({DoctorNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiError> handleDoctorNotFoundException(DoctorNotFoundException ex){
        ApiError apiError = new ApiError("No such doctor in the base", ex.getMessage());
        ResponseEntity<ApiError> responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
        return responseEntity;
    }

    @ExceptionHandler({DoctorDataValidationException.class})
    public ResponseEntity<ApiError>
        handleDoctorDataValidationException(DoctorDataValidationException e) {
        ApiError apiError = new ApiError("doctor data validation: ", e.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
