package ru.maslov.springstudyprpject.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.maslov.springstudyprpject.dto.AppointmentDTO;
import ru.maslov.springstudyprpject.dto.DoctorDTO;

import java.util.List;

@RestController
@RequestMapping(path = "/doctor_info")
public class DoctorUserController {
    /**
     * Получение информации врача о самом себе
     * @return DoctorDTO
     */
    @GetMapping
    public DoctorDTO getDoctorInfo() {
        return null;
    }

    /**
     * Измененеи информации врача о самом себе. Метод возвращает изменную информацию о самом себе
     * @return DoctorDTO
     */
    @PutMapping
    public DoctorDTO changeDoctorInfo() {
        return null;
    }

    /**
     * Возвращает список приемов, в которых принимает данный врач
     * @return List<AppointmentDTO>
     */
    @GetMapping(path = "/appointments")
    public List<AppointmentDTO> getAppointmentsOfDoctor() {

        return null;
    }

    /**
     * Метод для изменения статуса приема
     */
    @PutMapping(path = "/appointments/status")
    public AppointmentDTO changeAppointmentStatus() {
        return null;
    }

    /**
     * Метод для переноса записи врачом
     */
    @PutMapping(path = "/appointments/date")
    public AppointmentDTO changeAppointmentDateTime() {

        return null;
    }

    /**
     * Метод для изменения описания приема врачом
     */
    public AppointmentDTO changeAppointmentDescription() {

        return null;
    }
}
