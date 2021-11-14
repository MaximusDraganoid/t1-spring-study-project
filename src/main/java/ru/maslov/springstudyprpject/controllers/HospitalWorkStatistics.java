package ru.maslov.springstudyprpject.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.maslov.springstudyprpject.dto.DoctorDTO;
import ru.maslov.springstudyprpject.entities.Doctor;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/statics")
public class HospitalWorkStatistics {

    /**
     * Рассчитывает нагрузку на больницу в день недели
     */
    @GetMapping(path = "/by_day_of_week")
    public Map<DayOfWeek, Long> getStatisticsByDayOfWeek() {
        return new HashMap<>();
    }

    /**
     * Рассчитывает нагрузку в конкретный день недели по рабочим часам по количеству приемов
     */
    @GetMapping(path = "/by_hour")
    public void getStatisticsByHourOfDay() {

    }

    /**
     *  Рассчитывает нагрузку на каждого врача по количеству приемов (можно сделать за месяц)
     */
    @GetMapping(path = "/by_doctors") //_specialization
    public Map<DoctorDTO, Long> getStatisticsByDoctors() {
        return new HashMap<>();
    }

    /**
     *  Рассчитывает нагрузку на каждого врача по количеству приемов (можно сделать за месяц)
     */
    @GetMapping(path = "/by_doctors_specialization")
    public Map<DoctorDTO, Long> getStatisticsByDoctorsSpecialization() {
        return new HashMap<>();
    }
}
