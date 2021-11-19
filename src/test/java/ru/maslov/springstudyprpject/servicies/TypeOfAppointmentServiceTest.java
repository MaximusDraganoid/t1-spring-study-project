package ru.maslov.springstudyprpject.servicies;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.maslov.springstudyprpject.repositories.TypeOfAppointmentRepository;

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
}