package ru.maslov.springstudyprpject.servicies;

import org.springframework.stereotype.Service;
import ru.maslov.springstudyprpject.entities.Doctor;
import ru.maslov.springstudyprpject.exceptions.DoctorDataValidationException;
import ru.maslov.springstudyprpject.exceptions.DoctorNotFoundException;
import ru.maslov.springstudyprpject.repositories.DoctorRepository;

import java.util.List;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    private final UserService userService;

    public DoctorService(DoctorRepository doctorRepository, UserService userService) {
        this.doctorRepository = doctorRepository;
        this.userService = userService;
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

        return doctorRepository.save(doctor);
    }
}
