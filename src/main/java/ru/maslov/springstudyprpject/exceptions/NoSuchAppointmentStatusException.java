package ru.maslov.springstudyprpject.exceptions;

public class NoSuchAppointmentStatusException extends RuntimeException {
    public NoSuchAppointmentStatusException() {
    }

    public NoSuchAppointmentStatusException(String message) {
        super(message);
    }

    public NoSuchAppointmentStatusException(String message, Throwable cause) {
        super(message, cause);
    }
}
