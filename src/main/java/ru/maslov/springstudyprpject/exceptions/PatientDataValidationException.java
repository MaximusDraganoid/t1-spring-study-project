package ru.maslov.springstudyprpject.exceptions;

public class PatientDataValidationException extends RuntimeException{
    public PatientDataValidationException() {
    }

    public PatientDataValidationException(String message) {
        super(message);
    }

    public PatientDataValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
