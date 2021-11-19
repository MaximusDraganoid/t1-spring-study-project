package ru.maslov.springstudyprpject.servicies;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.maslov.springstudyprpject.repositories.DoctorSpecializationRepository;

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
}