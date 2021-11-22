package ru.maslov.springstudyprpject.servicies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.maslov.springstudyprpject.dto.AppointmentDTO;
import ru.maslov.springstudyprpject.entities.*;
import ru.maslov.springstudyprpject.exceptions.AppointmentDateFormatException;
import ru.maslov.springstudyprpject.exceptions.DoctorDataValidationException;
import ru.maslov.springstudyprpject.exceptions.DoctorNotFoundException;
import ru.maslov.springstudyprpject.exceptions.NoSuchAppointmentStatusException;
import ru.maslov.springstudyprpject.repositories.DoctorRepository;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Set;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    @Lazy
    @Autowired
    private AppointmentService appointmentService;

    private final PasswordEncoder passwordEncoder;

    private final PatientService patientService;

    public DoctorService(DoctorRepository doctorRepository,
                         PasswordEncoder passwordEncoder,
                         PatientService patientService) {
        this.doctorRepository = doctorRepository;
        this.passwordEncoder = passwordEncoder;
        this.patientService = patientService;
    }

    public Doctor getSelfInfo() {
        String currentUserLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        return doctorRepository.findByLogin(currentUserLogin).orElseThrow(() -> {
            throw new UsernameNotFoundException("failed to find self user information by login: " + currentUserLogin);
        });
    }

    public Doctor changeSelfInfo(Doctor updatedDoctor) {
        Doctor doctor = getSelfInfo();

        doctor.setName(updatedDoctor.getName());
        doctor.setPatronymic(updatedDoctor.getPatronymic());
        doctor.setSurname(updatedDoctor.getSurname());

        if (!doctor.getLogin().equals(updatedDoctor.getLogin())) {
            if (doctorRepository.findByLogin(updatedDoctor.getLogin()).isPresent()) {
                throw new DoctorDataValidationException("doctor with login "
                        + doctor.getLogin() + " already exists");
            }

            doctor.setLogin(updatedDoctor.getLogin());
        }

        if (!doctor.getPhoneNumber().equals(updatedDoctor.getPhoneNumber())) {
            if (doctorRepository.findByPhoneNumber(updatedDoctor.getPhoneNumber()).isPresent()) {
                throw new DoctorDataValidationException("patient with phone number "
                        + doctor.getPhoneNumber() + " already exists");
            }
            doctor.setPhoneNumber(updatedDoctor.getPhoneNumber());
        }

        return doctorRepository.save(doctor);
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

    public Doctor save(Doctor doctor) {
        if (doctorRepository.findByLogin(doctor.getLogin()).isPresent()) {
            throw new DoctorDataValidationException("login " + doctor.getLogin() + " exist in base");
        }

        if(doctorRepository.findByPhoneNumber(doctor.getPhoneNumber()).isPresent()) {
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

    public List<DoctorsSchedule> getSelfSchedule() {
        return getSelfInfo().getScheduleList();
    }

    public List<DoctorsSchedule> updateSchedule(List<DoctorsSchedule> doctorsSchedules) {

        return null;
    }

    public Set<Appointment> getSelfAppointments() {
        return appointmentService.findActualAppointmentByDoctor(getSelfInfo());
    }

    public Appointment changeAppointmentStatus(Long appointmentId, String statusName) {
        Appointment appointment = appointmentService.findAppointmentById(appointmentId);
        try {
            appointment.setStatus(StatusOfAppointment.valueOf(statusName));
        } catch (IllegalArgumentException e) {
            throw new NoSuchAppointmentStatusException("appointment with status " + statusName + " not found");
        }
        return appointmentService.saveAppointment(appointment);
    }

    public Appointment changeDateOfAppointment(Long appointmentId, String dateTime) {
        Appointment appointment = appointmentService.findAppointmentById(appointmentId);
        LocalDateTime newDate;
        try {
            newDate = LocalDateTime.parse(dateTime); //todo: обработка исключений в случае неверного формата данных
        } catch (DateTimeParseException e) {
            throw new AppointmentDateFormatException("format of dateTime "
                    + dateTime
                    + " not supported");
        }
        appointment.setDataTimeOfAppointment(newDate);
        return appointmentService.saveAppointment(appointment);

    }

    public Appointment changeDescriptionOfAppointment(Long appointmentId, String description) {
        Appointment appointment = appointmentService.findAppointmentById(appointmentId);
        appointment.setDescription(description);
        return appointmentService.saveAppointment(appointment);
    }

    public Appointment assignAppointmentForPatient(Appointment appointment) {
        return appointmentService.saveAppointment(appointment);
    }

    public List<Patient> getDoctorsPatients() {
        Doctor currentDoctor = getSelfInfo();
        return patientService.getPatientsByDoctor(currentDoctor);
    }

    public Set<Appointment> getAppointmentOfPatients(Long id) {
        return appointmentService.findAppointmentByPatientId(id);
    }

    public void addAppointmentToDoctor(Doctor doctor, Appointment appointment) {
        doctor = getById(doctor.getId());
        doctor.getAppointments().add(appointment);
        doctorRepository.save(doctor);
    }
}
