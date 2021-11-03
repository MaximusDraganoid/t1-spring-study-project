package ru.maslov.springstudyprpject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.maslov.springstudyprpject.entities.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}
