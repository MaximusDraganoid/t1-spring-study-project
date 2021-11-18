package ru.maslov.springstudyprpject.repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.maslov.springstudyprpject.entities.DoctorsSpecialization;
import ru.maslov.springstudyprpject.entities.TypeOfAppointment;

import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class TypeOfAppointmentRepositoryTest {

    @Autowired
    private DoctorSpecializationRepository doctorSpecializationRepository;

    @Autowired
    private TypeOfAppointmentRepository typeOfAppointmentRepositoryTest;

    @BeforeEach
    void setUp() {
        TypeOfAppointment examination = new TypeOfAppointment("Осмотр", Duration.ofMinutes(30));
        typeOfAppointmentRepositoryTest.save(examination);

        DoctorsSpecialization surgeon = new DoctorsSpecialization("Хирург", new HashSet<>());
        Set<TypeOfAppointment> surgeonTypeOfAppointment = surgeon.getTypeOfAppointmentSet();
        surgeonTypeOfAppointment.add(examination);
        doctorSpecializationRepository.save(surgeon);
    }

    @AfterEach
    void tearDown() {
        doctorSpecializationRepository.deleteAll();
        typeOfAppointmentRepositoryTest.deleteAll();
    }

    @Test
    void canFindTypeOfAppointmentBySpecializationId() {
        //given
        List<DoctorsSpecialization> specializations = doctorSpecializationRepository.findAll();
        DoctorsSpecialization searchedSpecializations = specializations.get(0);
        Set<TypeOfAppointment> expectedSet = searchedSpecializations.getTypeOfAppointmentSet();
        //when
        Set<TypeOfAppointment> searchedTypes = typeOfAppointmentRepositoryTest.findTypeOfAppointmentBySpecializationId(searchedSpecializations.getId());
        //then
        assertThat(searchedTypes.size()).isEqualTo(expectedSet.size());
        assertThat(searchedTypes).isEqualTo(expectedSet);
    }

    @Test
    void canNotFindTypeOfAppointmentBySpecializationId() {
        //given
        DoctorsSpecialization optometrist = new DoctorsSpecialization("Окулист", new HashSet<>());
        optometrist = doctorSpecializationRepository.save(optometrist);
        int expectedValue = 0;
        //when
        Set<TypeOfAppointment> searchedTypes = typeOfAppointmentRepositoryTest.findTypeOfAppointmentBySpecializationId(optometrist.getId());
        //then
        assertThat(searchedTypes.size()).isEqualTo(expectedValue);
    }
}