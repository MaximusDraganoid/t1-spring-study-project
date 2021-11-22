package ru.maslov.springstudyprpject.repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import ru.maslov.springstudyprpject.entities.Doctor;
import ru.maslov.springstudyprpject.entities.DoctorsSchedule;
import ru.maslov.springstudyprpject.entities.DoctorsSpecialization;
import ru.maslov.springstudyprpject.entities.Role;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest //конфигурация, которая автоматически настраивает репоситории для тестирования (сканит энтити, настривает репозитории и доступ к in-memory db).
        //application context при этом загружается не полностью, а только необходимые детали
@TestPropertySource("classpath:unit-test-application.properties")
class DoctorRepositoryTest {

    @Autowired
    private DoctorRepository doctorRepositoryTest;

    @Autowired
    private DoctorScheduleRepository doctorScheduleRepository;

    @Autowired
    private DoctorSpecializationRepository doctorSpecializationRepository;

    @AfterEach
    void tearDown() {
        doctorScheduleRepository.deleteAll();
        doctorSpecializationRepository.deleteAll();
        doctorRepositoryTest.deleteAll();
    }

    @BeforeEach
    void setUp() {
        DoctorsSpecialization surgeon = new DoctorsSpecialization("Хирург", new HashSet<>());
        doctorSpecializationRepository.saveAll(List.of(surgeon));

        Doctor firstDoctor = new Doctor("Иван",
                "Иванович",
                "Иванов",
                "ivanovii",
                "ai_is_coming",
                "79106272033",
                List.of(Role.DOCTOR),
                surgeon,
                new LinkedList<>(),
                new HashSet<>());

        doctorRepositoryTest.saveAll(List.of(firstDoctor));

        DoctorsSchedule firstDoctorScheduleDay = new DoctorsSchedule(firstDoctor,
                DayOfWeek.MONDAY,
                LocalTime.of(8, 0, 0),
                LocalTime.of(18, 0, 0));

        doctorScheduleRepository.saveAll(List.of(firstDoctorScheduleDay));
    }

    @Test
    void canFindDoctorForAppointmentsRecordByDate() {
        //given
        int expectedValue = 1;
        DayOfWeek monday = DayOfWeek.MONDAY;
        DoctorsSpecialization surgeon = doctorSpecializationRepository.findAll().get(0);
        String expectedLogin = "ivanovii";
        //when
        List<Doctor> foundDoctors = doctorRepositoryTest.getDoctorForAppointmentsRecordByDate(monday, surgeon);
        //then
        assertThat(foundDoctors.size()).isEqualTo(expectedValue);
        assertThat(foundDoctors.get(0).getLogin().equals(expectedLogin));
    }

    @Test
    void canNotFindDoctorForAppointmentsRecordByDate() {
        //given
        int expectedValue = 0;
        DayOfWeek wednesday = DayOfWeek.WEDNESDAY;
        DoctorsSpecialization surgeon = doctorSpecializationRepository.findAll().get(0);
        //when
        List<Doctor> foundDoctors = doctorRepositoryTest.getDoctorForAppointmentsRecordByDate(wednesday, surgeon);
        //then
        assertThat(foundDoctors.size()).isEqualTo(expectedValue);
    }

    @Test
    void canNotFindDoctorForAppointmentsRecordByDoctorsSpecialization() {
        //given
        int expectedValue = 0;
        DayOfWeek monday = DayOfWeek.MONDAY;
        DoctorsSpecialization testSpec = new DoctorsSpecialization("test_spec", new HashSet<>());
        testSpec = doctorSpecializationRepository.save(testSpec);
        //when
        List<Doctor> foundDoctors = doctorRepositoryTest.getDoctorForAppointmentsRecordByDate(monday, testSpec);
        //then
        assertThat(foundDoctors.size()).isEqualTo(expectedValue);
    }


    @Test
    public void canFindDoctorByLogin() {
        //given
        String searchedLogin = "ivanovii";
        //when
        Optional<Doctor> foundDoctor = doctorRepositoryTest.findByLogin(searchedLogin);
        //then
        assertThat(foundDoctor.isPresent()).isTrue();
        assertThat(foundDoctor.get().getLogin()).isEqualTo(searchedLogin);
    }

    @Test
    public void canNotFindDoctorByLogin() {
        //given
        String searchedLogin = "ivanov1ii";
        //when
        Optional<Doctor> foundDoctor = doctorRepositoryTest.findByLogin(searchedLogin);
        //then
        assertThat(foundDoctor.isPresent()).isFalse();
    }

    @Test
    public void canFindDoctorByPhoneNumer() {
        //given
        String searchedPhoneNumber = "79106272033";
        //when
        Optional<Doctor> foundDoctor = doctorRepositoryTest.findByPhoneNumber(searchedPhoneNumber);
        //then
        assertThat(foundDoctor.isPresent()).isTrue();
    }

    @Test
    public void canNotFindDoctorByPhoneNumber() {
        //given
        String searchedPhoneNumber = "79106272032";
        //when
        Optional<Doctor> foundDoctor = doctorRepositoryTest.findByLogin(searchedPhoneNumber);
        //then
        assertThat(foundDoctor.isPresent()).isFalse();
    }
}