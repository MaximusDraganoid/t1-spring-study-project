package ru.maslov.springstudyprpject.controllers;

import org.springframework.web.bind.annotation.*;
import ru.maslov.springstudyprpject.dto.AppointmentDTO;
import ru.maslov.springstudyprpject.dto.PatientDTO;

import javax.validation.constraints.NotNull;
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
     * Возвращает список приемов, на которые записан пациент (как историю приемов, так и только актуальные)
     *
     * @return List<AppointmentDTO>
     */
    @GetMapping(path = "/appointments")
    public List<AppointmentDTO> getAppointmentsOfPatient(@RequestParam("onlyActual") Boolean actual) {

        return null;
    }

    /**
     * Пользователь может отменить прием
     * @param id
     */
    @DeleteMapping(path = "/appointments")
    public void denyAppointment(@RequestParam("appointment_id") Long id) {

    }

    /**
     * Возвращает список возможных приемов на указанную дату к указанному специалисту на указанный тип приема (? - по идее
     * тип приема определяет врач, поэтому здесь должно осуществляться первичное врачебное вмещательство -
     * типа приема/консультации, поэтому есть смысл выбрасывать исключении, если передается тип Operation
     * todo: перенести логику работы из AppointmentController
     */
    @GetMapping(path = "/appointments/booking")
    public List<AppointmentDTO> getAppointmentBySpecializationAndData(@RequestParam("spec_id") @NotNull Long specializationId,
                                                                      @RequestParam("data") @NotNull String date,
                                                                      @RequestParam("appointment_type_id") @NotNull Long appointmentTypeId) {

        return null;
    }

    @PostMapping(path = "/appointments/booking")
    public AppointmentDTO createAppointment (@RequestBody AppointmentDTO appointmentDTO) {
        return null;
    }
}
