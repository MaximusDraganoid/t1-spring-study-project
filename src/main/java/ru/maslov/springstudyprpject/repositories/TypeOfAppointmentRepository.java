package ru.maslov.springstudyprpject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.maslov.springstudyprpject.entities.TypeOfAppointment;

public interface TypeOfAppointmentRepository extends JpaRepository<TypeOfAppointment, Long> {
}
