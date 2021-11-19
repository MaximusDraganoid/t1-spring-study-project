package ru.maslov.springstudyprpject.repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.maslov.springstudyprpject.entities.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class PatientRepositoryTest {

    @Autowired
    private PatientRepository patientRepositoryTest;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private TypeOfAppointmentRepository typeOfAppointmentRepository;

    @Autowired
    private DoctorSpecializationRepository doctorSpecializationRepository;


    @BeforeEach
    void setUp() {
        Patient secondPatient = new Patient("Александр",
                "Максимович",
                "Шумилов",
                "shumilovam",
                "shum12",
                "79106272031",
                List.of(Role.PATIENT),
                "1234567890123457",
                new HashSet<>());

                patientRepositoryTest.save(secondPatient);
    }

    @AfterEach
    void tearDown() {
        patientRepositoryTest.deleteAll();
    }

    @Test
    void canFindPatientsByDoctor() {
        //given
        TypeOfAppointment examination = new TypeOfAppointment("Осмотр", Duration.ofMinutes(30));
        typeOfAppointmentRepository.save(examination);
        DoctorsSpecialization surgeon = new DoctorsSpecialization("Хирург", new HashSet<>());
        doctorSpecializationRepository.save(surgeon);
        Patient patient = new Patient("Иван",
                "Сергеевич",
                "Тюрин",
                "tuirnis",
                "1vaN",
                "79106272030",
                List.of(Role.PATIENT),
                "1234567890123456",
                new HashSet<>());
        patient = patientRepositoryTest.save(patient);

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
        doctor = doctorRepository.save(doctor);

        Appointment appointment = new Appointment(patient,
                doctor,
                LocalDateTime.now(),
                examination,
                StatusOfAppointment.PLANNED,
                "test_description");
        appointmentRepository.saveAll(List.of(appointment, appointment, appointment));
        int expectedSizeOfList = 1;
        //when
        List<Patient> patientsByDoctor = patientRepositoryTest.findDistinctPatientsByDoctor(doctor);
        //then
        assertThat(patientsByDoctor.size()).isEqualTo(expectedSizeOfList);
        assertThat(patientsByDoctor.get(0).getId()).isEqualTo(patient.getId());
    }

    @Test
    void canNotFindPatientsByDoctor() {

    }

    @Test
    void canFindByPolicyNumber() {
        //given
        String searchedNumber = "1234567890123457";
        //when
        Optional<Patient> foundPatient = patientRepositoryTest.findByPolicyNumber(searchedNumber);
        //then
        assertThat(foundPatient.isPresent()).isTrue();
        assertThat(foundPatient.get().getPolicyNumber()).isEqualTo(searchedNumber);
    }

    @Test
    void canNotFindByPolicyNumber() {
        //given
        String searchedNumber = "1234567890123452";
        //when
        Optional<Patient> foundPatient = patientRepositoryTest.findByPolicyNumber(searchedNumber);
        //then
        assertThat(foundPatient.isPresent()).isFalse();
    }

    @Test
    void canFindByPhoneNumber() {
        //given
        String searchedPhoneNumber = "79106272031";
        //when
        Optional<Patient> foundPatient = patientRepositoryTest.findByPhoneNumber(searchedPhoneNumber);
        //then
        assertThat(foundPatient.isPresent()).isTrue();
        assertThat(foundPatient.get().getPhoneNumber()).isEqualTo(searchedPhoneNumber);
    }

    @Test
    void canNotFindByPhoneNumber() {
        //given
        String searchedPhoneNumber = "79106272039";
        //when
        Optional<Patient> foundPatient = patientRepositoryTest.findByPhoneNumber(searchedPhoneNumber);
        //then
        assertThat(foundPatient.isPresent()).isFalse();
    }

    @Test
    void canFindByLogin() {
        //given
        String searchedLogin = "shumilovam";
        //when
        Optional<Patient> foundPatient = patientRepositoryTest.findByLogin(searchedLogin);
        //then
        assertThat(foundPatient.isPresent()).isTrue();
        assertThat(foundPatient.get().getLogin()).isEqualTo(searchedLogin);
    }

    @Test
    void canNotFindByLogin() {
        //given
        String searchedLogin = "shumilov2am";
        //when
        Optional<Patient> foundPatient = patientRepositoryTest.findByLogin(searchedLogin);
        //then
        assertThat(foundPatient.isPresent()).isFalse();
    }
}