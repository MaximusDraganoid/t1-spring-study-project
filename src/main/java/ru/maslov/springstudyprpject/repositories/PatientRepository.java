package ru.maslov.springstudyprpject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.maslov.springstudyprpject.entities.Doctor;
import ru.maslov.springstudyprpject.entities.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByPolicyNumber(String policyNumber);
    Optional<Patient> findByPhoneNumber(String phoneNumber);
    Optional<Patient> findByLogin(String login);

    @Query("SELECT DISTINCT(patient) FROM Appointment WHERE doctor = :doctor")
    List<Patient> findPatientsByDoctor(@Param("doctor") Doctor doctor);
}
