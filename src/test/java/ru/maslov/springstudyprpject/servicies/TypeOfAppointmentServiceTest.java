package ru.maslov.springstudyprpject.servicies;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.maslov.springstudyprpject.entities.TypeOfAppointment;
import ru.maslov.springstudyprpject.exceptions.TypeOfAppointmentNotFoundException;
import ru.maslov.springstudyprpject.repositories.TypeOfAppointmentRepository;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TypeOfAppointmentServiceTest {

    private TypeOfAppointmentService testingService;

    @Mock
    private TypeOfAppointmentRepository repository;

    @BeforeEach
    void setUp() {
        testingService = new TypeOfAppointmentService(repository);
    }

    @Test
    void canGetTypesBySpecializationId() {
        //given
        Long specializationId = 1L;
        //when
        testingService.getTypesBySpecializationId(specializationId);
        //then
        verify(repository).findTypeOfAppointmentBySpecializationId(specializationId);
    }

    @Test
    void canFindTypeOfAppointmentById() {
        //given
        Long id = 1L;
        Mockito.doReturn(Optional.of(new TypeOfAppointment()))
                .when(repository)
                .findById(id);
        //when
        TypeOfAppointment foundType = testingService.findById(id);
        //then
        assertThat(foundType).isNotNull();
        verify(repository).findById(id);
    }

    @Test
    void canNotFindTypeOfAppointmentById() {
        //given
        Long wrongId = 22L;
        Mockito.doReturn(Optional.empty())
                .when(repository)
                .findById(wrongId);
        //when
        //then
        assertThatThrownBy(() -> testingService.findById(wrongId))
                .isInstanceOf(TypeOfAppointmentNotFoundException.class)
                .hasMessage("type of appointment with id " +
                        wrongId +
                        " not found");
    }
}