package ru.maslov.springstudyprpject.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import ru.maslov.springstudyprpject.dto.AppointmentDTO;
import ru.maslov.springstudyprpject.dto.mappers.AppointmentMapper;
import ru.maslov.springstudyprpject.entities.*;
import ru.maslov.springstudyprpject.servicies.DoctorService;
import ru.maslov.springstudyprpject.servicies.PatientService;
import ru.maslov.springstudyprpject.servicies.TypeOfAppointmentService;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:integration-test-application.properties") //специальная аннотация для подгрузки нужных .properties для интеграционного тестирования
class AppointmentControllerTest {

    private static final String LOCAL_HOST = "http://localhost:";
    private static final String BASE_PATH = "/appointments";

    @Autowired
    private AppointmentMapper appointmentMapper;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private TypeOfAppointmentService typeOfAppointmentService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void canCreateAppointment() {
        //given
        Long patientId = 8L;
        Patient patient = patientService.getById(patientId);

        Long doctorId = 11L;
        Doctor doctor = doctorService.getById(doctorId);

        Long typeOfAppointmentId = 1L;
        TypeOfAppointment typeOfAppointment = typeOfAppointmentService.getTypeById(typeOfAppointmentId);

        Appointment testAppointment = new Appointment(patient,
                doctor,
                LocalDateTime.now(),
                typeOfAppointment,
                StatusOfAppointment.PLANNED,
                "Описание приема");
        //when
        AppointmentDTO resultOfAddingAppointment = restTemplate.postForObject(LOCAL_HOST + port + BASE_PATH,
                appointmentMapper.toAppointmentDTO(testAppointment), AppointmentDTO.class);
        //then
        assertThat(resultOfAddingAppointment.getId()).isNotNull();
    }

    @Test
    void canNotCreateAppointmentBecauseNoSuchPatientInDB() {
        //given
        Long patientId = 8L;
        Long wrongId = 12L;
        Patient patient = patientService.getById(patientId);
        patient.setId(wrongId);

        Long doctorId = 11L;
        Doctor doctor = doctorService.getById(doctorId);

        Long typeOfAppointmentId = 1L;
        TypeOfAppointment typeOfAppointment = typeOfAppointmentService.getTypeById(typeOfAppointmentId);

        Appointment testAppointment = new Appointment(patient,
                doctor,
                LocalDateTime.now(),
                typeOfAppointment,
                StatusOfAppointment.PLANNED,
                "Описание приема");
        //when
        ResponseEntity<AppointmentDTO> appointmentDTOResponseEntity = restTemplate.postForEntity(LOCAL_HOST + port + BASE_PATH, appointmentMapper.toAppointmentDTO(testAppointment), AppointmentDTO.class);
        //then
        assertThat(appointmentDTOResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void canNotCreateAppointmentBecauseNoSuchDoctorInDB() {
        //given
        Long patientId = 8L;
        Patient patient = patientService.getById(patientId);

        Long wrongDoctorId = 55L;
        Long doctorId = 11L;
        Doctor doctor = doctorService.getById(doctorId);
        doctor.setId(wrongDoctorId);

        Long typeOfAppointmentId = 1L;
        TypeOfAppointment typeOfAppointment = typeOfAppointmentService.getTypeById(typeOfAppointmentId);

        Appointment testAppointment = new Appointment(patient,
                doctor,
                LocalDateTime.now(),
                typeOfAppointment,
                StatusOfAppointment.PLANNED,
                "Описание приема");
        //when
        ResponseEntity<AppointmentDTO> appointmentDTOResponseEntity = restTemplate.postForEntity(LOCAL_HOST + port + BASE_PATH, appointmentMapper.toAppointmentDTO(testAppointment), AppointmentDTO.class);
        //then
        assertThat(appointmentDTOResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}