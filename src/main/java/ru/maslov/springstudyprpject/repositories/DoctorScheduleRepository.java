package ru.maslov.springstudyprpject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.maslov.springstudyprpject.entities.DoctorsSchedule;

public interface DoctorScheduleRepository extends JpaRepository<DoctorsSchedule, Long> {
}
