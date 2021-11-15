package ru.maslov.springstudyprpject.controllers;

import org.springframework.web.bind.annotation.*;
import ru.maslov.springstudyprpject.dto.AppointmentDTO;
import ru.maslov.springstudyprpject.dto.PatientDTO;
import ru.maslov.springstudyprpject.dto.mappers.AppointmentMapper;
import ru.maslov.springstudyprpject.dto.mappers.PatientMapper;
import ru.maslov.springstudyprpject.entities.Appointment;
import ru.maslov.springstudyprpject.entities.Patient;
import ru.maslov.springstudyprpject.servicies.PatientService;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    private final PatientService patientService;
    private final PatientMapper patientMapper;
    private final AppointmentMapper appointmentMapper;

    public PatientUserController(PatientService patientService, PatientMapper patientMapper, AppointmentMapper appointmentMapper) {
        this.patientService = patientService;
        this.patientMapper = patientMapper;
        this.appointmentMapper = appointmentMapper;
    }

    @GetMapping
    public Patient getPatientInfo() {
        return patientService.getSelfInfo();
    }

    @PutMapping
    public Patient changeSelfPatientInfo(@RequestBody Patient updatedPatient) {
        return patientService.changeSelfPatientInfo(updatedPatient);
    }

    @DeleteMapping
    public Patient deletePatientFromSystem() {
        return patientService.selfDeleteUser();
    }

    /**
     * Возвращает список приемов, на которые записан пациент (как историю приемов, так и только актуальные)
     *
     * @return List<AppointmentDTO>
     */
    @GetMapping(path = "/appointments")
    public List<AppointmentDTO> getAppointmentsOfPatient(@RequestParam("onlyActual") Boolean actual) {
        return patientService.getSelfAppointments(actual)
                .stream().map(appointmentMapper::toAppointmentDTO)
                .collect(Collectors.toList());
    }

    /**
     * Пользователь может отменить прием
     * @param id - id отменяемого приема
     */
    @DeleteMapping(path = "/appointments")
    public void denyAppointment(@RequestParam("appointment_id") Long id) {
        //todo:
    }

    /**
     * Возвращает список возможных приемов на указанную дату к указанному специалисту на указанный тип приема (? - по идее
     * тип приема определяет врач, поэтому здесь должно осуществляться первичное врачебное вмещательство -
     * типа приема/консультации, поэтому есть смысл выбрасывать исключении, если передается тип Operation
     */
    @GetMapping(path = "/appointments/booking")
    public List<AppointmentDTO> getAppointmentBySpecializationAndData(@RequestParam("spec_id") @NotNull Long specializationId,
                                                                      @RequestParam("data") @NotNull String date,
                                                                      @RequestParam("appointment_type_id") @NotNull Long appointmentTypeId) {
        Set<Appointment> appointments = patientService.getAppointmentsForBooking(specializationId, date, appointmentTypeId);
        return appointments
                .stream()
                .map(appointmentMapper::toAppointmentDTO)
                .collect(Collectors.toList());
    }

    @PostMapping(path = "/appointments/booking")
    public AppointmentDTO createAppointment (@RequestBody AppointmentDTO appointmentDTO) {
        Appointment appointment = patientService.createAppointmentForPatient(appointmentMapper.toAppointment(appointmentDTO));
        return appointmentMapper.toAppointmentDTO(appointment);
    }
}
