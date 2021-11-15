package ru.maslov.springstudyprpject.servicies;

import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

    public PatientService(PatientRepository patientRepository, UserService userService, PasswordEncoder passwordEncoder) {
        this.patientRepository = patientRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
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

        if (patientRepository.findByLogin(patient.getLogin()).isPresent()) {
            throw new PatientDataValidationException("patient with login "
                    + patient.getLogin() + " already exists");
        }

        if (patientRepository.findByPhoneNumber(patient.getPhoneNumber()).isPresent()) {
            throw new PatientDataValidationException("patient with phone number "
                    + patient.getPhoneNumber() + " already exists");
        }

        if (patientRepository.findByPolicyNumber(patient.getPolicyNumber()).isPresent()) {
            throw new PatientDataValidationException("patient with policy number "
                    + patient.getPolicyNumber() + " already exists");
        }

        patient.setPassword(passwordEncoder.encode(patient.getPassword()));
        return patientRepository.save(patient);
    }

    public Patient changePatientData(Patient updatedPatient) {
        Patient patient
                = patientRepository.findById(updatedPatient.getId()).orElseThrow(() -> {
            throw new PatientNotFoundException("patient with id "
                    + updatedPatient.getId() +
                    " doesn't exist in base");
        });

        patient.setName(updatedPatient.getName());
        patient.setPatronymic(updatedPatient.getPatronymic());
        patient.setSurname(updatedPatient.getSurname());

        if (patientRepository.findByLogin(updatedPatient.getLogin()).isPresent()) {
            throw new PatientDataValidationException("patient with login "
                    + patient.getLogin() + " already exists");
        }
        patient.setLogin(updatedPatient.getLogin());
        patient.setPassword(updatedPatient.getPassword());

        if (patientRepository.findByPhoneNumber(updatedPatient.getPhoneNumber()).isPresent()) {
            throw new PatientDataValidationException("patient with phone number "
                    + patient.getPhoneNumber() + " already exists");
        }
        patient.setPhoneNumber(updatedPatient.getPhoneNumber());

        if (patientRepository.findByPolicyNumber(updatedPatient.getPolicyNumber()).isPresent()) {
            throw new PatientDataValidationException("patient with policy number "
                    + patient.getPolicyNumber() + " already exists");
        }
        patient.setPolicyNumber(updatedPatient.getPolicyNumber());


        return patientRepository.save(patient);
    }
}
