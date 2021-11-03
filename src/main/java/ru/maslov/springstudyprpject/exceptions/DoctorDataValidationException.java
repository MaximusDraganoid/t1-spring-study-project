package ru.maslov.springstudyprpject.exceptions;

public class DoctorDataValidationException extends RuntimeException{
    public DoctorDataValidationException() {
    }

    public DoctorDataValidationException(String message) {
        super(message);
    }

    public DoctorDataValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
