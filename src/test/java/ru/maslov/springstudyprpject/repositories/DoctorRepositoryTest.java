package ru.maslov.springstudyprpject.repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.maslov.springstudyprpject.entities.*;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest //конфигурация, которая автоматически настраивает репоситории для тестирования (сканит энтити, настривает репозитории и доступ к in-memory db).
        //application context при этом загружается не полностью, а только необходимые детали
class DoctorRepositoryTest {

    @Autowired
    private DoctorRepository doctorRepositoryTest;

    @Autowired
    private DoctorScheduleRepository doctorScheduleRepository;

    @Autowired
    private TypeOfAppointmentRepository typeOfAppointmentRepository;

    @Autowired
    private DoctorSpecializationRepository doctorSpecializationRepository;

    @AfterEach
    void tearDown() {
        doctorRepositoryTest.deleteAll();
    }

    @Test
    void canFindDoctorForAppointmentsRecordByDate() {
        //given
        TypeOfAppointment examination = new TypeOfAppointment("Осмотр", Duration.ofMinutes(30));
        typeOfAppointmentRepository.saveAll(List.of(examination));

        DoctorsSpecialization surgeon = new DoctorsSpecialization("Хирург", new HashSet<>());
        Set<TypeOfAppointment> surgeonTypeOfAppointment = surgeon.getTypeOfAppointmentSet();
        surgeonTypeOfAppointment.addAll(List.of(examination));
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

        Doctor secondDoctor = new Doctor("Иван",
                "Иванович",
                "Иванов",
                "ivanovii",
                "ai_is_coming",
                "79106272033",
                List.of(Role.DOCTOR),
                surgeon,
                new LinkedList<>(),
                new HashSet<>());

        doctorRepositoryTest.saveAll(List.of(firstDoctor, secondDoctor));

        DoctorsSchedule firstDoctorScheduleDay = new DoctorsSchedule(firstDoctor,
                DayOfWeek.MONDAY,
                LocalTime.of(8, 0, 0),
                LocalTime.of(18, 0, 0));

        DoctorsSchedule secondDoctorScheduleDay = new DoctorsSchedule(firstDoctor,
                DayOfWeek.WEDNESDAY,
                LocalTime.of(8, 0, 0),
                LocalTime.of(18, 0, 0));
        doctorScheduleRepository.saveAll(List.of(firstDoctorScheduleDay, secondDoctorScheduleDay));
        //when
        List<Doctor> foundDoctors = doctorRepositoryTest.getDoctorForAppointmentsRecordByDate(DayOfWeek.MONDAY, surgeon);
        //then
        int expectedValue = 1;
        assertThat(foundDoctors.size()).isEqualTo(expectedValue);
        assertThat(foundDoctors.get(0).getLogin().equals(firstDoctor.getLogin()));
    }

    @Test
    void canNotFindDoctorForAppointmentsRecordByDate() {
        //given
        TypeOfAppointment examination = new TypeOfAppointment("Осмотр", Duration.ofMinutes(30));
        typeOfAppointmentRepository.saveAll(List.of(examination));

        DoctorsSpecialization surgeon = new DoctorsSpecialization("Хирург", new HashSet<>());
        Set<TypeOfAppointment> surgeonTypeOfAppointment = surgeon.getTypeOfAppointmentSet();
        surgeonTypeOfAppointment.addAll(List.of(examination));
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

        Doctor secondDoctor = new Doctor("Иван",
                "Иванович",
                "Иванов",
                "ivanovii",
                "ai_is_coming",
                "79106272033",
                List.of(Role.DOCTOR),
                surgeon,
                new LinkedList<>(),
                new HashSet<>());

        doctorRepositoryTest.saveAll(List.of(firstDoctor, secondDoctor));

        DoctorsSchedule firstDoctorScheduleDay = new DoctorsSchedule(firstDoctor,
                DayOfWeek.TUESDAY,
                LocalTime.of(8, 0, 0),
                LocalTime.of(18, 0, 0));

        DoctorsSchedule secondDoctorScheduleDay = new DoctorsSchedule(firstDoctor,
                DayOfWeek.WEDNESDAY,
                LocalTime.of(8, 0, 0),
                LocalTime.of(18, 0, 0));
        doctorScheduleRepository.saveAll(List.of(firstDoctorScheduleDay, secondDoctorScheduleDay));
        //when
        List<Doctor> foundDoctors = doctorRepositoryTest.getDoctorForAppointmentsRecordByDate(DayOfWeek.MONDAY, surgeon);
        //then
        int expectedValue = 0;
        assertThat(foundDoctors.size()).isEqualTo(expectedValue);
    }

    @Test
    public void canFindDoctorByLogin() {
        //given
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
        doctorRepositoryTest.save(firstDoctor);
        //when
        Optional<Doctor> foundDoctor = doctorRepositoryTest.findByLogin("ivanovii");
        //then
        assertThat(foundDoctor.isPresent()).isTrue();
    }

    @Test
    public void canNotFindDoctorByLogin() {
        //given
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
        doctorRepositoryTest.save(firstDoctor);
        //when
        Optional<Doctor> foundDoctor = doctorRepositoryTest.findByLogin("ivanov1ii");
        //then
        assertThat(foundDoctor.isPresent()).isFalse();
    }

    @Test
    public void canFindDoctorByPhoneNumer() {
        //given
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
        doctorRepositoryTest.save(firstDoctor);
        //when
        Optional<Doctor> foundDoctor = doctorRepositoryTest.findByPhoneNumber("79106272033");
        //then
        assertThat(foundDoctor.isPresent()).isTrue();
    }

    @Test
    public void canNotFindDoctorByPhoneNumber() {
        //given
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
        doctorRepositoryTest.save(firstDoctor);
        //when
        Optional<Doctor> foundDoctor = doctorRepositoryTest.findByLogin("79106272032");
        //then
        assertThat(foundDoctor.isPresent()).isFalse();
    }
}