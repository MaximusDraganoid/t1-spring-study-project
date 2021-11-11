package ru.maslov.springstudyprpject.servicies;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.maslov.springstudyprpject.entities.Doctor;
import ru.maslov.springstudyprpject.entities.DoctorsSpecialization;
import ru.maslov.springstudyprpject.exceptions.DoctorDataValidationException;
import ru.maslov.springstudyprpject.exceptions.DoctorNotFoundException;
import ru.maslov.springstudyprpject.repositories.DoctorRepository;

import java.time.DayOfWeek;
import java.util.List;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public DoctorService(DoctorRepository doctorRepository, UserService userService, PasswordEncoder passwordEncoder) {
        this.doctorRepository = doctorRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Doctor> getList() {
        return doctorRepository.findAll();
    }

    public Doctor getById(Long id) {
        return doctorRepository.findById(id).orElseThrow(() -> {
            throw new DoctorNotFoundException("doctor with id " + id + " does not exist in base");
        });
    }

    public void deleteById(Long id) {
        Doctor doctor = doctorRepository.findById(id).orElseThrow(() -> {
            throw new DoctorNotFoundException("doctor with id " + id + " does not exist in base");
        });
        doctorRepository.delete(doctor);
    }

    public Doctor create(Doctor doctor) {
        if (userService.findByLogin(doctor.getLogin()).isPresent()) {
            throw new DoctorDataValidationException("login " + doctor.getLogin() + " exist in base");
        }

        if(userService.findByPhoneNumber(doctor.getPhoneNumber()).isPresent()) {
            throw new DoctorDataValidationException("phone number "
                    + doctor.getPhoneNumber() + " exist in base");
        }
        doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));
        return doctorRepository.save(doctor);
    }

    public List<Doctor> getDoctorForAppointmentsRecordByDate(DayOfWeek dayOfWeek,
                                                      DoctorsSpecialization specialization) {
        return doctorRepository.getDoctorForAppointmentsRecordByDate(dayOfWeek, specialization);
    }
}
