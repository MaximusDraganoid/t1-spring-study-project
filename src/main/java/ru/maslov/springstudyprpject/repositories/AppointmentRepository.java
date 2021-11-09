package ru.maslov.springstudyprpject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.maslov.springstudyprpject.entities.Appointment;
import ru.maslov.springstudyprpject.entities.Patient;

import java.util.Set;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    @Query("FROM Appointment where patient.id = :patientId")
    Set<Appointment> findAppointmentByPatientId(@Param("patientId")Long patientId);
}
