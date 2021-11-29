package ru.maslov.springstudyprpject.controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import ru.maslov.springstudyprpject.dto.AppointmentDTO;
import ru.maslov.springstudyprpject.dto.mappers.AppointmentMapper;
import ru.maslov.springstudyprpject.entities.*;
import ru.maslov.springstudyprpject.servicies.AppointmentService;
import ru.maslov.springstudyprpject.servicies.DoctorService;
import ru.maslov.springstudyprpject.servicies.PatientService;
import ru.maslov.springstudyprpject.servicies.TypeOfAppointmentService;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

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

    @Autowired
    private AppointmentService appointmentService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @AfterEach
    void deleteAllAppointments() {
        appointmentService.deleteAll();
    }

    @Test
    void canCreateAppointment() {
        //given
        Long patientId = 8L;
        Patient patient = patientService.getById(patientId);

        Long doctorId = 11L;
        Doctor doctor = doctorService.getById(doctorId);

        Long typeOfAppointmentId = 1L;
        TypeOfAppointment typeOfAppointment = typeOfAppointmentService.findById(typeOfAppointmentId);

        Appointment testAppointment = new Appointment(patient,
                doctor,
                LocalDateTime.now(),
                typeOfAppointment,
                StatusOfAppointment.PLANNED,
                "Описание приема");
        //when
        ResponseEntity<AppointmentDTO> resultOfAddingAppointment = restTemplate.postForEntity(LOCAL_HOST + port + BASE_PATH,
                appointmentMapper.toAppointmentDTO(testAppointment), AppointmentDTO.class);
        //then
//        assertThat(resultOfAddingAppointment.getId()).isNotNull();
        System.out.println(resultOfAddingAppointment.getStatusCode());
    }

    @Test
    void canNotCreateAppointmentBecauseNoSuchPatientInDB() {
        //given
        Long patientId = 8L;
        Long wrongId = 0L;
        Patient patient = patientService.getById(patientId);
        patient.setId(wrongId);

        Long doctorId = 11L;
        Doctor doctor = doctorService.getById(doctorId);

        Long typeOfAppointmentId = 1L;
        TypeOfAppointment typeOfAppointment = typeOfAppointmentService.findById(typeOfAppointmentId);

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
        TypeOfAppointment typeOfAppointment = typeOfAppointmentService.findById(typeOfAppointmentId);

        Appointment testAppointment = new Appointment(patient,
                doctor,
                LocalDateTime.now(),
                typeOfAppointment,
                StatusOfAppointment.PLANNED,
                "Описание приема");
        //when
        ResponseEntity<AppointmentDTO> appointmentDTOResponseEntity =
                restTemplate.postForEntity(LOCAL_HOST + port + BASE_PATH,
                        appointmentMapper.toAppointmentDTO(testAppointment),
                        AppointmentDTO.class);
        //then
        assertThat(appointmentDTOResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void canNotCreateAppointmentBecauseBodyHasNullField() throws MalformedURLException, URISyntaxException {
        ///given
        Long patientId = 8L;
        Patient patient = patientService.getById(patientId);

        Long wrongDoctorId = 55L;
        Long doctorId = 11L;
        Doctor doctor = doctorService.getById(doctorId);
        doctor.setId(wrongDoctorId);

        Long typeOfAppointmentId = 1L;
        TypeOfAppointment typeOfAppointment = typeOfAppointmentService.findById(typeOfAppointmentId);

        Appointment testAppointment = new Appointment(null,
                doctor,
                LocalDateTime.now(),
                typeOfAppointment,
                StatusOfAppointment.PLANNED,
                "Описание приема");
        //when

        ResponseEntity<AppointmentDTO> appointmentDTOResponseEntity =
                restTemplate.postForEntity(LOCAL_HOST + port + BASE_PATH,
                        appointmentMapper.toAppointmentDTO(testAppointment),
                        AppointmentDTO.class);
        //then
        assertThat(appointmentDTOResponseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void canNotCreateAppointmentBecauseBodyIsEmpty() {
        ///given
        HashMap emptyBody = new HashMap<>();
        //when
        ResponseEntity<AppointmentDTO> appointmentDTOResponseEntity =
                restTemplate.postForEntity(LOCAL_HOST + port + BASE_PATH,
                        emptyBody,
                        AppointmentDTO.class);
        //then
        assertThat(appointmentDTOResponseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void canGetAppointmentBySpecializationAndData() {
        //given
        long specializationId = 6L;
        String date = "2021-11-29";
        long appointmentTypeId = 1L;
        long patientId = 8L;
        String queryString = "?spec_id=" + specializationId
                + "&appointment_type_id=" + appointmentTypeId
                + "&data=" + date
                + "&patient_id=" + patientId;
        String url = LOCAL_HOST + port + BASE_PATH + queryString;
        int expectedListSize = 78;
        //when
        //https://www.baeldung.com/spring-resttemplate-json-list
        ResponseEntity<List<AppointmentDTO>> responseEntity =
                restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {}
                );

        List<AppointmentDTO> appointmentDTOList = responseEntity.getBody();
        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(appointmentDTOList.size()).isEqualTo(expectedListSize);
    }

    @Test
    void canNotGetAppointmentBecauseNoNeededQueryParams() {
        //given
        long specializationId = 6L;
        String date = "2021-11-29";
        long appointmentTypeId = 1L;
        String queryString = "?spec_id=" + specializationId
                + "&appointment_type_id=" + appointmentTypeId
                + "&data=" + date;
        String url = LOCAL_HOST + port + BASE_PATH + queryString;
        //when
        ResponseEntity<HashMap<String, String>> responseEntity =
                restTemplate.exchange(url,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {});
        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void canNotGetAppointmentBecauseTakenWrongArguments() {
        //given
        long specializationId = 88L;
        String date = "2021-11-29";
        long appointmentTypeId = 1L;
        long patientId = 8L;
        String queryString = "?spec_id=" + specializationId
                + "&appointment_type_id=" + appointmentTypeId
                + "&data=" + date
                + "&patient_id=" + patientId;
        String url = LOCAL_HOST + port + BASE_PATH + queryString;
        //when
        ResponseEntity<HashMap<String, String>> responseEntity =
                restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {}
                );
        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}