package ru.maslov.springstudyprpject.servicies;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import ru.maslov.springstudyprpject.entities.Appointment;
import ru.maslov.springstudyprpject.entities.Patient;
import ru.maslov.springstudyprpject.exceptions.PatientDataValidationException;
import ru.maslov.springstudyprpject.exceptions.PatientNotFoundException;
import ru.maslov.springstudyprpject.repositories.PatientRepository;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class PatientService {
    private final PatientRepository patientRepository;
    private final PasswordEncoder passwordEncoder;
    private final AppointmentService appointmentService;

    public PatientService(PatientRepository patientRepository, PasswordEncoder passwordEncoder, AppointmentService appointmentService) {
        this.patientRepository = patientRepository;
        this.passwordEncoder = passwordEncoder;
        this.appointmentService = appointmentService;
    }

    public Set<Appointment> getSelfAppointments (Boolean withHistoryOfAppointments) {
        Patient currentPatient = getSelfInfo();
        Set<Appointment> resultAppointments;
        if (withHistoryOfAppointments) {
            resultAppointments = appointmentService.findAppointmentByPatientId(currentPatient.getId());
        } else {
            resultAppointments = appointmentService.findActualAppointmentByPatientId(currentPatient.getId());
        }
        return resultAppointments;
    }

    public Patient getSelfInfo() {
        String currentUserLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        return patientRepository.findByLogin(currentUserLogin).orElseThrow(() -> {
            throw new UsernameNotFoundException("failed to find self user information by login: " + currentUserLogin);
        });
    }

    public Patient selfDeleteUser() {
        Patient currentPatient = getSelfInfo();
        patientRepository.delete(currentPatient);
        return currentPatient;
    }

    public Patient changeSelfPatientInfo(Patient updatedPatient) {
        Patient currentPatient = getSelfInfo();
        return changePatientData(currentPatient, updatedPatient);
    }

    public Set<Appointment> getAppointmentsForBooking(Long specializationId, String date, Long appointmentTypeId) {
        return appointmentService.getAppointmentBySpecializationIdAndData(specializationId, date, appointmentTypeId);
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

        return changePatientData(patient, updatedPatient);
    }

    private Patient changePatientData(Patient patient, Patient updatedPatient) {

        patient.setName(updatedPatient.getName());
        patient.setPatronymic(updatedPatient.getPatronymic());
        patient.setSurname(updatedPatient.getSurname());

        if (!patient.getLogin().equals(updatedPatient.getLogin())) {
            if (patientRepository.findByLogin(updatedPatient.getLogin()).isPresent()) {
                throw new PatientDataValidationException("patient with login "
                        + patient.getLogin() + " already exists");
            }

            patient.setLogin(updatedPatient.getLogin());
        }

        if (!patient.getPassword().equals(updatedPatient.getPassword())) {
            patient.setPassword(passwordEncoder.encode(updatedPatient.getPassword()));
        }

        if (!patient.getPhoneNumber().equals(updatedPatient.getPhoneNumber())) {
            if (patientRepository.findByPhoneNumber(updatedPatient.getPhoneNumber()).isPresent()) {
                throw new PatientDataValidationException("patient with phone number "
                        + patient.getPhoneNumber() + " already exists");
            }
            patient.setPhoneNumber(updatedPatient.getPhoneNumber());
        }


        if (!patient.getPolicyNumber().equals(updatedPatient.getPolicyNumber())) {
            if (patientRepository.findByPolicyNumber(updatedPatient.getPolicyNumber()).isPresent()) {
                throw new PatientDataValidationException("patient with policy number "
                        + patient.getPolicyNumber() + " already exists");
            }
            patient.setPolicyNumber(updatedPatient.getPolicyNumber());
        }

        return patientRepository.save(patient);
    }

    public Appointment createAppointmentForPatient(Appointment appointment) {
        return appointmentService.createAppointment(appointment);
    }
}
