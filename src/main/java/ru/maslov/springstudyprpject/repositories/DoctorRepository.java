package ru.maslov.springstudyprpject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.maslov.springstudyprpject.entities.Doctor;
import ru.maslov.springstudyprpject.entities.DoctorsSpecialization;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    @Query("SELECT DISTINCT d FROM Doctor d " +
            "INNER JOIN DoctorsSchedule dsch " +
            "ON d = dsch.doctor " +
            "WHERE dsch.dayOfWeek = :dayOfWeek AND d.specialization = :specialization")
    List<Doctor> getDoctorForAppointmentsRecordByDate(@Param("dayOfWeek")DayOfWeek dayOfWeek,
                                                      @Param("specialization")DoctorsSpecialization specialization);
    Optional<Doctor> findByLogin(String login);
    Optional<Doctor> findByPhoneNumber(String phoneNumber);

}
