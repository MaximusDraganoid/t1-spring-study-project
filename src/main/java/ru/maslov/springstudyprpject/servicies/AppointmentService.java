package ru.maslov.springstudyprpject.servicies;

import org.springframework.stereotype.Service;
import ru.maslov.springstudyprpject.entities.*;
import ru.maslov.springstudyprpject.exceptions.AppointmentDateFormatException;
import ru.maslov.springstudyprpject.exceptions.AppointmentNotFoundException;
import ru.maslov.springstudyprpject.repositories.AppointmentRepository;

import java.time.*;
import java.time.format.DateTimeParseException;
import java.util.*;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;

    private final DoctorsSpecializationService doctorsSpecializationService;


    private final TypeOfAppointmentService typeOfAppointmentService;
    private final PatientService patientService;
    private final DoctorService doctorService;

    public AppointmentService(AppointmentRepository appointmentRepository,
                              DoctorsSpecializationService doctorsSpecializationService,
                              TypeOfAppointmentService typeOfAppointmentService,
                              PatientService patientService,
                              DoctorService doctorService) {
        this.appointmentRepository = appointmentRepository;
        this.doctorsSpecializationService = doctorsSpecializationService;
        this.typeOfAppointmentService = typeOfAppointmentService;
        this.patientService = patientService;
        this.doctorService = doctorService;
    }

    public Appointment findAppointmentById(Long id) {
        return appointmentRepository.findById(id).orElseThrow(() -> {
            throw new AppointmentNotFoundException("appointment with id " + id + " not found");
        });
    }

    public Set<Appointment> findAppointmentByPatientId(Long id) {
        return appointmentRepository.findAppointmentByPatientId(id);
    }

    public Set<Appointment> findActualAppointmentByPatientId(Long id) {
        return appointmentRepository.findActualAppointmentsForPatients(id, LocalDateTime.now());
    }

    public Set<Appointment> findActualAppointmentByDoctor(Doctor doctor) {
        return appointmentRepository.findActualAppointmentsForDoctor(doctor, LocalDateTime.now());
    }

    public Appointment saveAppointment(Appointment appointment) {

        appointment = appointmentRepository.save(appointment);

        patientService.addAppointmentToPatient(appointment.getPatient(), appointment);
        doctorService.addAppointmentToDoctor(appointment.getDoctor(), appointment);
        return appointment;
    }


    //todo: ????????????????
    public Set<Appointment> getAppointmentBySpecializationIdAndData(Long specializationId,
                                                                    String data,
                                                                    Long appointmentTypeId,
                                                                    Long patientId) {
        LocalDate date;
        try {
            date = LocalDate.parse(data); //todo: ?????????????????? ???????????????????? ?? ???????????? ?????????????????? ?????????????? ????????????
        } catch (DateTimeParseException e) {
            throw new AppointmentDateFormatException("format of date "
                    + data
                    + " not supported");
        }
        Patient patient = patientService.getById(patientId);
        DoctorsSpecialization specialization = doctorsSpecializationService.findById(specializationId);

        TypeOfAppointment typeOfAppointment = typeOfAppointmentService.findById(appointmentTypeId);

        List<Doctor> doctors = doctorService.getDoctorForAppointmentsRecordByDate(date.getDayOfWeek(),
                specialization);

        Set<Appointment> resultAppointments =
                new TreeSet<>(Comparator
                        .comparing(Appointment::getDataTimeOfAppointment)
                        .thenComparing(a -> a.getDoctor().getLogin()));

        for (Doctor doctor : doctors) {
            DoctorsSchedule doctorsSchedule = findScheduleByDayOfWeek(date.getDayOfWeek(), doctor);
            SortedSet<Appointment> sortedDoctorsAppointmentSet =
                    new TreeSet<>(Comparator
                            .comparing(Appointment::getDataTimeOfAppointment));
            sortedDoctorsAppointmentSet.addAll(doctor.getAppointments());

            LocalTime startWorkTime = doctorsSchedule.getStartTime();
            LocalTime finishWorkTime = doctorsSchedule.getFinishTime();
            do {
                if (startWorkTime.plus(typeOfAppointment.getDuration()).isBefore(finishWorkTime)
                        || startWorkTime.plus(typeOfAppointment.getDuration()).equals(finishWorkTime)) {
                    if (!isCrossingAppointments(sortedDoctorsAppointmentSet.iterator(),
                            startWorkTime,
                            typeOfAppointment.getDuration())) {
                        Appointment appointment = new Appointment(patient,
                                doctor,
                                startWorkTime.atDate(date),
                                typeOfAppointment,
                                StatusOfAppointment.PLANNED,
                                "base_description");
                        resultAppointments.add(appointment);
                    }
                }
                startWorkTime = startWorkTime.plus(typeOfAppointment.getDuration().dividedBy(2L));

            } while (startWorkTime.isBefore(finishWorkTime));
        }

        return resultAppointments;
    }

    private boolean isCrossingAppointments(Iterator<Appointment> iterator,
                                           LocalTime predictedStartWorkTime,
                                           Duration duration) {
        while (iterator.hasNext()) {
            Appointment currentAppointment = iterator.next();
            LocalTime startAppointmentTime = currentAppointment.getDataTimeOfAppointment().toLocalTime();
            LocalTime finishAppointmentTime = startAppointmentTime
                    .plus(currentAppointment.getTypeOfAppointment().getDuration());

            if (timeBetween(predictedStartWorkTime, startAppointmentTime, finishAppointmentTime)
                    || timeBetween(predictedStartWorkTime.plus(duration), startAppointmentTime, finishAppointmentTime)
                    || durationIn(predictedStartWorkTime, duration, startAppointmentTime, finishAppointmentTime)) {
                return true;
            }
        }
        return false;
    }

    private boolean timeBetween(LocalTime targetTime, LocalTime start, LocalTime end) {
        return (targetTime.isBefore(end) || targetTime.equals(end))
                && (targetTime.isAfter(start) || targetTime.equals(start));
    }

    private boolean durationIn(LocalTime targetTime, Duration duration, LocalTime start, LocalTime end) {
        return (targetTime.isBefore(start) || targetTime.equals(start))
                && (targetTime.plus(duration).isAfter(end) || targetTime.plus(duration).equals(end));
    }

    private DoctorsSchedule findScheduleByDayOfWeek(DayOfWeek dayOfWeek, Doctor doctor) {
        return doctor.getScheduleList()
                .stream()
                .filter(doctorsSchedule -> doctorsSchedule.getDayOfWeek().equals(dayOfWeek))
                .findFirst()
                .get();
    }

    public void deleteAll() {
        appointmentRepository.deleteAll();
    }
}