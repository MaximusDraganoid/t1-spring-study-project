package ru.maslov.springstudyprpject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.maslov.springstudyprpject.entities.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
