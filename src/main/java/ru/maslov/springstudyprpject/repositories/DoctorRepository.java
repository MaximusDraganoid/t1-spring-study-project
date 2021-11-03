package ru.maslov.springstudyprpject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.maslov.springstudyprpject.entities.Doctor;

import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    @Query("FROM Doctor where login = :login")
    Optional<Doctor> findByLogin(@Param("login") String login);

    @Query("FROM Doctor where phoneNumber = :phoneNumber")
    Optional<Doctor> findByPhoneNumber(@Param("phoneNumber") String phoneNumber);
}
