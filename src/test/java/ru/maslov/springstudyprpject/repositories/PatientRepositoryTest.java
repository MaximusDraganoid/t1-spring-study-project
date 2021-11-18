package ru.maslov.springstudyprpject.repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.maslov.springstudyprpject.entities.Patient;
import ru.maslov.springstudyprpject.entities.Role;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PatientRepositoryTest {

    @Autowired
    private PatientRepository patientRepositoryTest;

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