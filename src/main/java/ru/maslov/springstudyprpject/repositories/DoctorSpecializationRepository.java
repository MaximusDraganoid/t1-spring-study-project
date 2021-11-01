package ru.maslov.springstudyprpject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.maslov.springstudyprpject.entities.DoctorsSpecialization;

public interface DoctorSpecializationRepository extends JpaRepository<DoctorsSpecialization, Long> {
}
