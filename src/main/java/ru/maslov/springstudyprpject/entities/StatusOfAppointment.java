package ru.maslov.springstudyprpject.entities;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;

public enum StatusOfAppointment {
    APPOINTED, COMPLETED, DENIED, PLANNED, ERROR;

    @JsonCreator
    public static StatusOfAppointment getByValue(Integer statusCode) {
        return Arrays.stream(StatusOfAppointment.values())
                .filter(a -> a.ordinal() == statusCode).findFirst().orElse(StatusOfAppointment.ERROR);
    }

}
