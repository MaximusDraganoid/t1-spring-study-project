package ru.maslov.springstudyprpject.controllers;

import org.springframework.web.bind.annotation.*;
import ru.maslov.springstudyprpject.dto.AppointmentDTO;
import ru.maslov.springstudyprpject.dto.PatientDTO;

import java.util.List;

/**
 * Посредством этого класса пациент будет взаимодействовать со своей персональной информацией. Разделение сделано с целью,
 * чтобы пользователь не мог взаимодействовать с данными другими пользователей (эту проблему можно было бы решить, используя
 * дополнительные проверки при работе сервисоного слоя на то, что данный пользователь может выполнять те или иные операции с
 * указанным пользователем по id, т.е. что он действительно является тем, за кого себя выдает). По аналогичной причине
 * соотвтетсвующее разделение сделано и для обычного врача.
 */
@RestController
@RequestMapping(path = "/patient_info")
public class PatientUserController {

    @GetMapping
    public PatientDTO getPatientInfo() {

        return null;
    }

    @PutMapping
    public PatientDTO changePatientInfo() {

        return null;
    }

    @DeleteMapping
    public PatientDTO deletePatientFromSystem() {

        return null;
    }

    /**
     * Возвращает список приемов, на которые записан пациент
     * @return List<AppointmentDTO>
     */
    @GetMapping(path = "/appointments")
    public List<AppointmentDTO> getAppointmentsOfPatient() {

        return null;
    }
}
