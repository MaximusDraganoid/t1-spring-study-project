package ru.maslov.springstudyprpject.controllers;

import org.springframework.web.bind.annotation.*;
import ru.maslov.springstudyprpject.dto.AppointmentDTO;
import ru.maslov.springstudyprpject.dto.DoctorDTO;
import ru.maslov.springstudyprpject.dto.PatientDTO;
import ru.maslov.springstudyprpject.dto.mappers.AppointmentMapper;
import ru.maslov.springstudyprpject.dto.mappers.DoctorMapper;
import ru.maslov.springstudyprpject.dto.mappers.PatientMapper;
import ru.maslov.springstudyprpject.entities.Doctor;
import ru.maslov.springstudyprpject.entities.DoctorsSchedule;
import ru.maslov.springstudyprpject.servicies.DoctorService;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/doctor_info")
public class DoctorUserController {

    private final DoctorService doctorService;
    private final AppointmentMapper appointmentMapper;
    private final DoctorMapper doctorMapper;
    private final PatientMapper patientMapper;


    public DoctorUserController(DoctorService doctorService,
                                AppointmentMapper appointmentMapper,
                                DoctorMapper doctorMapper,
                                PatientMapper patientMapper) {
        this.doctorService = doctorService;
        this.appointmentMapper = appointmentMapper;
        this.doctorMapper = doctorMapper;
        this.patientMapper = patientMapper;
    }

    /**
     * Получение информации врача о самом себе
     * @return DoctorDTO
     */
    @GetMapping
    public Doctor getDoctorInfo() {
        return doctorService.getSelfInfo();
    }

    /**
     * Измененеи информации врача о самом себе. Метод возвращает изменную информацию о самом себе
     */
    @PutMapping
    public Doctor changeDoctorInfo(@RequestBody Doctor doctor) {
        return doctorService.changeSelfInfo(doctor);
    }


    @GetMapping(path = "/schedule")
    public List<DoctorsSchedule> getScheduleList() {
        return doctorService.getSelfSchedule();
    }

    @PutMapping(path = "/schedule")
    public List<DoctorsSchedule> updateScheduleList(@RequestBody List<DoctorsSchedule> doctorsSchedules) {
        //todo:
        return doctorService.updateSchedule(doctorsSchedules);
    }

    /**
     * Возвращает список приемов, в которых принимает данный врач
     * @return List<AppointmentDTO>
     */
    @GetMapping(path = "/appointments")
    public Set<AppointmentDTO> getAppointmentsOfDoctor() {
        return doctorService.getSelfAppointments()
                .stream()
                .map(appointmentMapper::toAppointmentDTO)
                .collect(Collectors.toSet());
    }

    /**
     * Метод для изменения статуса приема
     */
    @PutMapping(path = "/appointments/{id}/status")
    public AppointmentDTO changeAppointmentStatus(@PathVariable("id")@NotNull Long appointmentId,
                                                  @RequestParam("status_name") @NotNull String statusName) {
        return appointmentMapper.toAppointmentDTO(doctorService.changeAppointmentStatus(appointmentId, statusName));
    }

    /**
     * Метод для переноса записи врачом
     */
    @PutMapping(path = "/appointments/{id}/date")
    public AppointmentDTO changeAppointmentDateTime(@PathVariable("id") @NotNull Long appointmentId,
                                                    @RequestParam("date_time") @NotNull String dateTime) {
        doctorService.changeDateOfAppointment(appointmentId, dateTime);
        return appointmentMapper.toAppointmentDTO(doctorService.changeDateOfAppointment(appointmentId, dateTime));
    }

    /**
     * Метод для изменения описания приема врачом
     */
    @PutMapping(path = "/appointments/{id}/description")
    public AppointmentDTO changeAppointmentDescription(@PathVariable("id") @NotNull Long appointmentId,
                                                       @RequestBody @NotNull String description) {
        return appointmentMapper.toAppointmentDTO(doctorService.changeDescriptionOfAppointment(appointmentId, description));
    }

    /**
     * Метод для того, чтобы врач мог назначать прием пациенту
     * @param appointmentDTO
     * @return
     */
    @PostMapping(path = "/appointments")
    public AppointmentDTO assignAppointmentForPatient (@RequestBody AppointmentDTO appointmentDTO) {
        return appointmentMapper.toAppointmentDTO(doctorService.assignAppointmentForPatient(appointmentMapper.toAppointment(appointmentDTO)));
    }

    /**
     * Получение списка всех пациентов врача
     */
    @GetMapping(path = "/patients")
    public List<PatientDTO> getPatientsOfDoctor() {
        return doctorService.getDoctorsPatients()
                .stream()
                .map(patientMapper::toPatientDTO)
                .collect(Collectors.toList());
    }

    /**
     * Получение истории премов данного пациента
     */
    @GetMapping(path = "/patients/{id}")
    public Set<AppointmentDTO> getPatientsAppointmentsHistory(@PathVariable("id") Long id) {
        return doctorService.getAppointmentOfPatients(id)
                .stream()
                .map(appointmentMapper::toAppointmentDTO)
                .collect(Collectors.toSet());
    }
}
