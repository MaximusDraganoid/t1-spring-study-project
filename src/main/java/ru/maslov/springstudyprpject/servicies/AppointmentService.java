package ru.maslov.springstudyprpject.servicies;

import org.springframework.stereotype.Service;
import ru.maslov.springstudyprpject.entities.*;
import ru.maslov.springstudyprpject.exceptions.AppointmentDateFormatException;
import ru.maslov.springstudyprpject.exceptions.DoctorSpecializationNotFoundException;
import ru.maslov.springstudyprpject.exceptions.TypeOfAppointmentNotFoundException;
import ru.maslov.springstudyprpject.repositories.AppointmentRepository;
import ru.maslov.springstudyprpject.repositories.DoctorSpecializationRepository;
import ru.maslov.springstudyprpject.repositories.TypeOfAppointmentRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.*;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;

    private final DoctorSpecializationRepository doctorSpecializationRepository;

    private final TypeOfAppointmentRepository typeOfAppointmentRepository;

    private final DoctorService doctorService;

    public AppointmentService(AppointmentRepository appointmentRepository,
                              DoctorSpecializationRepository doctorSpecializationRepository,
                              TypeOfAppointmentRepository typeOfAppointmentRepository,
                              DoctorService doctorService) {
        this.appointmentRepository = appointmentRepository;
        this.doctorSpecializationRepository = doctorSpecializationRepository;
        this.typeOfAppointmentRepository = typeOfAppointmentRepository;
        this.doctorService = doctorService;
    }

    public Set<Appointment> findAppointmentByPatientId(Long id) {
        return appointmentRepository.findAppointmentByPatientId(id);
    }

    //todo: рефактор
    public Set<Appointment> getAppointmentBySpecializationIdAndData(Long specializationId,
                                                                     String data,
                                                                     Long appointmentTypeId) {
        LocalDate date;
        try {
            date = LocalDate.parse(data); //todo: обработка исключений в случае неверного формата данных
        } catch (DateTimeParseException e) {
            throw new AppointmentDateFormatException("format of date "
                    +  data
                    + " not supported");
        }
        Patient patient = new Patient(); //todo: похже изменить работу с введением sequrity
        DoctorsSpecialization specialization =
            doctorSpecializationRepository.findById(specializationId).orElseThrow(() -> {
                throw new DoctorSpecializationNotFoundException("doctors specialization with id " +
                        specializationId +
                        " not found");
            });

        TypeOfAppointment typeOfAppointment =
            typeOfAppointmentRepository.findById(appointmentTypeId).orElseThrow(() -> {
                throw new TypeOfAppointmentNotFoundException("type of appointment with id " +
                        appointmentTypeId +
                        " not found");
            });

        List<Doctor> doctors = doctorService.getDoctorForAppointmentsRecordByDate(date.getDayOfWeek(),
                specialization);

        Set<Appointment> resultAppointments =
                new TreeSet<>(Comparator.comparing(Appointment::getDataTimeOfAppointment));

        for (Doctor doctor : doctors) {
            DoctorsSchedule doctorsSchedule = findScheduleByDayOfWeek(date.getDayOfWeek(), doctor);
            LocalTime startWorkTime = doctorsSchedule.getStartTime();
            LocalTime finishWorkTime = doctorsSchedule.getFinishTime();
            SortedSet<Appointment> sortedDoctorsAppointmentSet =
                    new TreeSet<>(Comparator.comparing(Appointment::getDataTimeOfAppointment));
            sortedDoctorsAppointmentSet.addAll(doctor.getAppointments());

            //алгоритм обработки - нужно как то проверять возможно ли в уазанное время поставить прием?
            Iterator<Appointment> iterator = sortedDoctorsAppointmentSet.iterator();
            Appointment currentAppointment;
            do {
                if (iterator.hasNext()) {
                    currentAppointment = iterator.next();
                    finishWorkTime = currentAppointment
                            .getDataTimeOfAppointment()
                            .toLocalTime()
                            .isBefore(finishWorkTime) ?
                            currentAppointment.getDataTimeOfAppointment().toLocalTime() : finishWorkTime;
                } else {
                    finishWorkTime = doctorsSchedule.getFinishTime();
                }
                if (startWorkTime.plus(typeOfAppointment.getDuration()).isBefore(finishWorkTime)) {
                    Appointment appointment = new Appointment(patient,
                            doctor,
                            startWorkTime.atDate(date),
                            typeOfAppointment,
                            StatusOfAppointment.UNDER_CONSIDERATION,
                            "base_description");
                    resultAppointments.add(appointment);
                }
                startWorkTime = startWorkTime.plus(typeOfAppointment.getDuration());

            } while (startWorkTime.isBefore(finishWorkTime));
        }

        return resultAppointments;
    }

    private DoctorsSchedule findScheduleByDayOfWeek(DayOfWeek dayOfWeek, Doctor doctor) {
        return doctor.getScheduleList()
                .stream()
                .filter(doctorsSchedule -> doctorsSchedule.getDayOfWeek().equals(dayOfWeek))
                .findFirst()
                .get();
    }
}