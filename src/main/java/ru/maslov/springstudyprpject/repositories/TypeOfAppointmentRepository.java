package ru.maslov.springstudyprpject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.maslov.springstudyprpject.entities.TypeOfAppointment;

import java.util.Set;

public interface TypeOfAppointmentRepository extends JpaRepository<TypeOfAppointment, Long> {
    @Query("SELECT spec.typeOfAppointmentSet " +
            "FROM DoctorsSpecialization spec " +
            "WHERE spec.id = :id")
    Set<TypeOfAppointment> findTypeOfAppointmentBySpecializationId(@Param("id") Long id);
}
