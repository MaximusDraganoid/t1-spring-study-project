package ru.maslov.springstudyprpject.exceptions;

public class TypeOfAppointmentNotFoundException extends RuntimeException {
    public TypeOfAppointmentNotFoundException() {
    }

    public TypeOfAppointmentNotFoundException(String message) {
        super(message);
    }

    public TypeOfAppointmentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
