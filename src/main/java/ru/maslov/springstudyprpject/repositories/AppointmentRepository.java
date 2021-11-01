package ru.maslov.springstudyprpject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.maslov.springstudyprpject.entities.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
