package ru.maslov.springstudyprpject.servicies;

import org.springframework.stereotype.Service;
import ru.maslov.springstudyprpject.entities.DoctorsSpecialization;
import ru.maslov.springstudyprpject.exceptions.DoctorSpecializationNotFoundException;
import ru.maslov.springstudyprpject.repositories.DoctorSpecializationRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorsSpecializationService {

    private final DoctorSpecializationRepository doctorSpecializationRepository;

    public DoctorsSpecializationService(DoctorSpecializationRepository doctorSpecializationRepository) {
        this.doctorSpecializationRepository = doctorSpecializationRepository;
    }

    public List<DoctorsSpecialization> getAllSpecializations() {
        return doctorSpecializationRepository.findAll();
    }
}
