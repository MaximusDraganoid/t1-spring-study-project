package ru.maslov.springstudyprpject.repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;
import ru.maslov.springstudyprpject.entities.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AppointmentRepositoryTest {

    @Autowired
    private AppointmentRepository appointmentRepositoryTest;

    @Autowired
    private DoctorSpecializationRepository doctorSpecializationRepository;

    @Autowired
    TypeOfAppointmentRepository typeOfAppointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @BeforeEach
    void setUp() {

        TypeOfAppointment examination = new TypeOfAppointment("Осмотр", Duration.ofMinutes(30));
        typeOfAppointmentRepository.save(examination);

        DoctorsSpecialization surgeon = new DoctorsSpecialization("Хирург", new HashSet<>());
        doctorSpecializationRepository.save(surgeon);

        Doctor doctor = new Doctor("Иван",
                "Иванович",
                "Иванов",
                "ivanovii",
                "ai_is_coming",
                "79106272033",
                List.of(Role.DOCTOR),
                surgeon,
                new LinkedList<>(),
                new HashSet<>());

        Patient patient = new Patient("Кирилл",
                "Александрович",
                "Шубин",
                "root",
                "mafioz67",
                "79106272032",
                List.of(Role.PATIENT),
                "1234567890123458",
                new HashSet<>());

        Appointment appointment1 = new Appointment(patient,
                doctor,
                LocalDateTime.of(1992, 12, 12, 12, 12),
                examination,
                StatusOfAppointment.APPOINTED,
                "successful appointment");

        Appointment appointment2 = new Appointment(patient,
                doctor,
                LocalDateTime.of(2022, 12, 12, 12, 12),
                examination,
                StatusOfAppointment.PLANNED,
                "successful appointment");

        /*
        указанные ниже действия можно было бы заменить на этапе проектирования, сдела что то такое
        @OneToMany(fetch = FetchType.EAGER, targetEntity=Appointments.class, mappedBy="id")
        private Set<Appointments> appointments;
         */
        patient.getAppointmentSet().addAll(List.of(appointment1, appointment2));
        doctor.getAppointments().addAll(List.of(appointment1, appointment2));

        patientRepository.save(patient);
        doctorRepository.save(doctor);
        appointmentRepositoryTest.saveAll(List.of(appointment1, appointment2));
    }

    @AfterEach
    void tearDown() {
        appointmentRepositoryTest.deleteAll();
        patientRepository.deleteAll();
        doctorRepository.deleteAll();
        doctorSpecializationRepository.deleteAll();
        typeOfAppointmentRepository.deleteAll();
    }

    @Test
    void canFindAppointmentByPatientId() {
        //given
        Patient patient = patientRepository.findAll().get(0);
        Set<Appointment> expectedAppointmentSet = patient.getAppointmentSet();
        //when
        Set<Appointment> foundAppointmentSet = appointmentRepositoryTest.findAppointmentByPatientId(patient.getId());
        //then
        assertThat(foundAppointmentSet.size()).isEqualTo(expectedAppointmentSet.size());
        assertThat(foundAppointmentSet).isEqualTo(expectedAppointmentSet);
    }

    @Test
    void canNotFindAppointmentByPatientId() {
        //given
        Patient patient = patientRepository.findAll().get(0);
        int expectedSizeOfSet = 0;
        //when
        Set<Appointment> foundAppointmentSet = appointmentRepositoryTest.findAppointmentByPatientId(patient.getId() + 1);
        //then
        assertThat(foundAppointmentSet.size()).isEqualTo(expectedSizeOfSet);
    }

    @Test
    void canFindActualAppointmentsForPatients() {
        //given
        Patient patient = patientRepository.findAll().get(0);
        int expectedSizeOfSet = 1;
        //when
        Set<Appointment> actualAppointments = appointmentRepositoryTest.findActualAppointmentsForPatients(patient.getId(), LocalDateTime.now());
        //then
        assertThat(actualAppointments.size()).isEqualTo(expectedSizeOfSet);
    }

    @Test
    void canNotFindActualAppointmentsForPatientsBecauseWrongPatientId() {
        //given
        Patient patient = patientRepository.findAll().get(0);
        int expectedSizeOfSet = 0;
        //when
        Set<Appointment> actualAppointments = appointmentRepositoryTest.findActualAppointmentsForPatients(patient.getId() + 1, LocalDateTime.now());
        //then
        assertThat(actualAppointments.size()).isEqualTo(expectedSizeOfSet);
    }

    @Test
    void canNotFindActualAppointmentsForPatientsBecauseWrongDataTimeOfAppointment() {
        //given
        Patient patient = patientRepository.findAll().get(0);
        int expectedSizeOfSet = 0;
        //when
        Set<Appointment> actualAppointments = appointmentRepositoryTest.findActualAppointmentsForPatients(patient.getId(), LocalDateTime.now().plus(2, ChronoUnit.YEARS));
        //then
        assertThat(actualAppointments.size()).isEqualTo(expectedSizeOfSet);
    }

    @Test
    void canFindActualAppointmentsForDoctor() {
        //given
        Doctor doctor = doctorRepository.findAll().get(0);
        int expectedSizeOfSet = 1;
        //when
        Set<Appointment> actualAppointments = appointmentRepositoryTest.findActualAppointmentsForDoctor(doctor, LocalDateTime.now());
        //then
        assertThat(actualAppointments.size()).isEqualTo(expectedSizeOfSet);
    }

    @Test
    void canNotFindActualAppointmentsForDoctorBecauseWrongDoctor() {
        //given
        Doctor doctor = new Doctor("Петр",
                "Петрович",
                "Селедкин",
                "seledkinpp",
                "drop_the_data",
                "79106272035",
                List.of(Role.DOCTOR, Role.MAIN_DOCTOR),
                doctorSpecializationRepository.findAll().get(0),
                new LinkedList<>(),
                new HashSet<>());
        doctor = doctorRepository.save(doctor);
        int expectedSizeOfSet = 0;
        //when
        Set<Appointment> actualAppointments = appointmentRepositoryTest.findActualAppointmentsForDoctor(doctor, LocalDateTime.now());
        //then
        assertThat(actualAppointments.size()).isEqualTo(expectedSizeOfSet);
    }

    @Test
    void canNotFindActualAppointmentsForDoctorBecauseWrongDataTime() {
        //given
        Doctor doctor = doctorRepository.findAll().get(0);
        int expectedSizeOfSet = 0;
        //when
        Set<Appointment> actualAppointments = appointmentRepositoryTest.findActualAppointmentsForDoctor(doctor, LocalDateTime.now().plus(2, ChronoUnit.YEARS));
        //then
        assertThat(actualAppointments.size()).isEqualTo(expectedSizeOfSet);
    }
}