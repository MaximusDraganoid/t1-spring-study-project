package ru.maslov.springstudyprpject.servicies;

import org.springframework.stereotype.Service;
import ru.maslov.springstudyprpject.entities.Patient;
import ru.maslov.springstudyprpject.exceptions.PatientDataValidationException;
import ru.maslov.springstudyprpject.exceptions.PatientNotFoundException;
import ru.maslov.springstudyprpject.repositories.PatientRepository;

import java.util.List;

@Service
public class PatientService {
    private final PatientRepository patientRepository;
    private final UserService userService;

    public PatientService(PatientRepository patientRepository, UserService userService) {
        this.patientRepository = patientRepository;
        this.userService = userService;
    }

    public List<Patient> getPatients() {
        return patientRepository.findAll();
    }

    public Patient getById(Long id) {
        return patientRepository.findById(id).orElseThrow(
                () -> {
                    throw new PatientNotFoundException("patient with id "
                            + id +
                            " doesn't exist in base");
                }
        );
    }

    public void deleteById(Long id) {
        Patient patient = patientRepository.findById(id).orElseThrow(
                () -> {
                    throw new PatientNotFoundException("patient with id "
                            + id +
                            " doesn't exist in base");
                }
        );
        patientRepository.delete(patient);
    }

    public Patient create(Patient patient) {

        if (userService.findByLogin(patient.getLogin()).isPresent()) {
            throw new PatientDataValidationException("patient with login "
                    + patient.getLogin() + " already exists");
        }

        if (userService.findByPhoneNumber(patient.getPhoneNumber()).isPresent()) {
            throw new PatientDataValidationException("patient with phone number "
                    + patient.getPhoneNumber() + " already exists");
        }

        if (patientRepository.findByPolicyNumber(patient.getPolicyNumber()).isPresent()) {
            throw new PatientDataValidationException("patient with policy number "
                    + patient.getPolicyNumber() + " already exists");
        }

        return patientRepository.save(patient);
    }
}
