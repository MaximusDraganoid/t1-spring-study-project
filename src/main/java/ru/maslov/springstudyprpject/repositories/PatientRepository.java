package ru.maslov.springstudyprpject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.maslov.springstudyprpject.entities.Patient;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    @Query("FROM Patient where policyNumber = :policyNumber")
    Optional<Patient> findByPolicyNumber(@Param("policyNumber") String policyNumber);
}
