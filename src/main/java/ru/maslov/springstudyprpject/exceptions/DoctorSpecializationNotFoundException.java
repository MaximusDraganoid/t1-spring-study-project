package ru.maslov.springstudyprpject.exceptions;

public class DoctorSpecializationNotFoundException extends RuntimeException{
    public DoctorSpecializationNotFoundException() {
    }

    public DoctorSpecializationNotFoundException(String message) {
        super(message);
    }

    public DoctorSpecializationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
