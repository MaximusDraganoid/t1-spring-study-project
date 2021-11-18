package ru.maslov.springstudyprpject.repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.maslov.springstudyprpject.entities.*;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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
    void getDoctorForAppointmentsRecordByDate() {
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
}