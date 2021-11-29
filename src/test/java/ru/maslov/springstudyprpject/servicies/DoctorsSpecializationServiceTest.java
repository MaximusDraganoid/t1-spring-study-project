package ru.maslov.springstudyprpject.servicies;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.maslov.springstudyprpject.entities.DoctorsSpecialization;
import ru.maslov.springstudyprpject.exceptions.DoctorSpecializationNotFoundException;
import ru.maslov.springstudyprpject.repositories.DoctorSpecializationRepository;

import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.framework;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DoctorsSpecializationServiceTest {

    private DoctorsSpecializationService testingService;

    @Mock
    private DoctorSpecializationRepository repository;

    @BeforeEach
    void setUp() {
        testingService = new DoctorsSpecializationService(repository);
    }

    @Test
    void getAllSpecializations() {
        //given
        //when
        testingService.getAllSpecializations();
        //then
        verify(repository).findAll();
    }

    @Test
    void canFindById() {
        //given
        Long id = 4L;
        Mockito.doReturn(Optional.of(new DoctorsSpecialization()))
                .when(repository)
                .findById(id);
        //when
        DoctorsSpecialization doctorsSpecialization = testingService.findById(4L);
        //then
        verify(repository).findById(id);
        assertThat(doctorsSpecialization).isNotNull();
    }

    @Test
    void canNotFindById() {
        //given
        Long wrongId = 1L;
        Mockito.doReturn(Optional.empty())
                .when(repository)
                .findById(wrongId);
        //when
        //then
        assertThatThrownBy(() -> testingService.findById(wrongId))
                .isInstanceOf(DoctorSpecializationNotFoundException.class)
                .hasMessage("doctors specialization with id " +
                        wrongId +
                        " not found");
    }

    @Test
    void canSaveSpecialization() {
        //given
        DoctorsSpecialization newSpecialization = new DoctorsSpecialization("test_spec",
                new HashSet<>());
        //when
        testingService.saveSpecialization(newSpecialization);
        //then
        verify(repository).save(newSpecialization);
    }
}