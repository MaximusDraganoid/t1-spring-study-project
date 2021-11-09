package ru.maslov.springstudyprpject.exceptions;

public class AppointmentDateFormatException extends RuntimeException {
    public AppointmentDateFormatException() {
    }

    public AppointmentDateFormatException(String message) {
        super(message);
    }

    public AppointmentDateFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
