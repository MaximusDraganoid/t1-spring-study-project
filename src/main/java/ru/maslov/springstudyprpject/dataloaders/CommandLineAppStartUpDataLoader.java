package ru.maslov.springstudyprpject.dataloaders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import ru.maslov.springstudyprpject.entities.*;
import ru.maslov.springstudyprpject.repositories.*;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.*;

@Component
public class CommandLineAppStartUpDataLoader implements CommandLineRunner {

    private DoctorSpecializationRepository doctorSpecializationRepository;

    private TypeOfAppointmentRepository typeOfAppointmentRepository;

    private PatientRepository patientRepository;

    private DoctorRepository doctorRepository;

    private DoctorScheduleRepository doctorScheduleRepository;


    @Override
    public void run(String... args) {
        Pageable pageable = PageRequest.of(0, 1);

        //typeOfAppointment initialization
        TypeOfAppointment examination = new TypeOfAppointment("Осмотр", Duration.ofMinutes(30));
        TypeOfAppointment operation = new TypeOfAppointment("Операция", Duration.ofHours(3));
        TypeOfAppointment consultation = new TypeOfAppointment("Консультация", Duration.ofMinutes(45));

        if (typeOfAppointmentRepository.findAll(pageable).getTotalElements() == 0) {
            typeOfAppointmentRepository.saveAll(List.of(examination, operation, consultation));
        }

        //doctor's specialization initialization
        if (doctorSpecializationRepository.findAll(pageable).getTotalElements() == 0) {
            DoctorsSpecialization surgeon = new DoctorsSpecialization("Хирург", new HashSet<>());
            Set<TypeOfAppointment> surgeonTypeOfAppointment = surgeon.getTypeOfAppointmentSet();
            surgeonTypeOfAppointment.addAll(List.of(examination, operation));

            DoctorsSpecialization optometrist = new DoctorsSpecialization("Окулист", new HashSet<>());
            Set<TypeOfAppointment> optometristTypeOfAppointment = optometrist.getTypeOfAppointmentSet();
            optometristTypeOfAppointment.addAll(List.of(examination, consultation, operation));

            DoctorsSpecialization therapist = new DoctorsSpecialization("Терапевт", new HashSet<>());
            Set<TypeOfAppointment> therapistTypeOfAppointment = therapist.getTypeOfAppointmentSet();
            therapistTypeOfAppointment.addAll(List.of(examination, consultation));

            doctorSpecializationRepository.saveAll(List.of(surgeon, optometrist, therapist));
        }

        if (patientRepository.findAll(pageable).getTotalElements() == 0) {
            Patient firstPatient = new Patient("Иван",
                    "Сергеевич",
                    "Тюрин",
                    "tuirnis",
                    "1vaN",
                    "79106272030",
                    "1234567890123456",
                    new HashSet<>());

            Patient secondPatient = new Patient("Александр",
                    "Максимович",
                    "Шумилов",
                    "shumilovam",
                    "shum12",
                    "79106272031",
                    "1234567890123457",
                    new HashSet<>());

            Patient thirdPatient = new Patient("Кирилл",
                    "Александрович",
                    "Шубин",
                    "root",
                    "mafioz67",
                    "79106272032",
                    "1234567890123458",
                    new HashSet<>());

            patientRepository.saveAll(List.of(firstPatient, secondPatient, thirdPatient));
        }

        if (doctorRepository.findAll(pageable).getTotalElements() == 0) {
            Random generator = new Random(System.nanoTime());
            List<DoctorsSpecialization> specializations = doctorSpecializationRepository.findAll();
            Doctor firstDoctor = new Doctor("Иван",
                    "Иванович",
                    "Иванов",
                    "ivanovii",
                    "ai_is_coming",
                    "79106272033",
                    specializations.get(0),
                    new LinkedList<>(),
                    new HashSet<>());

            Doctor secondDoctor = new Doctor("Петр",
                    "Петрович",
                    "Селедкин",
                    "seledkinpp",
                    "drop_the_data",
                    "79106272035",
                    specializations.get(1),
                    new LinkedList<>(),
                    new HashSet<>());

            Doctor thirdDoctor = new Doctor("Семен",
                    "Петрович",
                    "Екатин",
                    "ekatinsp",
                    "drop_the_data",
                    "79106272335",
                    specializations.get(2),
                    new LinkedList<>(),
                    new HashSet<>());

            Doctor fourthDoctor = new Doctor("Кирилл",
                    "Евгеньевич",
                    "Бахтин",
                    "bachtinke",
                    "drop_the_data",
                    "79106372335",
                    specializations.get(2),
                    new LinkedList<>(),
                    new HashSet<>());

            doctorRepository.saveAll(List.of(firstDoctor, secondDoctor, thirdDoctor, fourthDoctor));

            DoctorsSchedule firstDoctorScheduleFirstDay = initAllDay(firstDoctor, DayOfWeek.MONDAY);
            DoctorsSchedule firstDoctorScheduleSecondDay = initAllDay(firstDoctor, DayOfWeek.WEDNESDAY);
            DoctorsSchedule firstDoctorScheduleThirdDay = initFirstHalfDay(firstDoctor, DayOfWeek.FRIDAY);

            DoctorsSchedule secondDoctorScheduleFirstDay = initAllDay(secondDoctor, DayOfWeek.TUESDAY);
            DoctorsSchedule secondDoctorScheduleSecondDay = initAllDay(secondDoctor, DayOfWeek.THURSDAY);
            DoctorsSchedule secondDoctorScheduleThirdDay = initSecondHalfDay(secondDoctor, DayOfWeek.FRIDAY);

            DoctorsSchedule thirdDoctorScheduleFirstDay = initAllDay(thirdDoctor, DayOfWeek.MONDAY);
            DoctorsSchedule thirdDoctorScheduleSecondDay = initAllDay(thirdDoctor, DayOfWeek.WEDNESDAY);
            DoctorsSchedule thirdDoctorScheduleThirdDay = initFirstHalfDay(thirdDoctor, DayOfWeek.FRIDAY);

            DoctorsSchedule fourthDoctorScheduleFirstDay = initAllDay(fourthDoctor, DayOfWeek.MONDAY);
            DoctorsSchedule fourthDoctorScheduleSecondDay = initAllDay(fourthDoctor, DayOfWeek.WEDNESDAY);
            DoctorsSchedule fourthDoctorScheduleThirdDay = initFirstHalfDay(fourthDoctor, DayOfWeek.FRIDAY);

            doctorScheduleRepository.saveAll(List.of(firstDoctorScheduleFirstDay, firstDoctorScheduleSecondDay,
                    firstDoctorScheduleThirdDay, secondDoctorScheduleFirstDay,
                    secondDoctorScheduleSecondDay, secondDoctorScheduleThirdDay,
                    thirdDoctorScheduleFirstDay, thirdDoctorScheduleSecondDay,
                    thirdDoctorScheduleThirdDay, fourthDoctorScheduleFirstDay,
                    fourthDoctorScheduleSecondDay, fourthDoctorScheduleThirdDay
                    ));
        }
    }

    @Autowired
    public void setDoctorSpecializationRepository(DoctorSpecializationRepository doctorSpecializationRepository) {
        this.doctorSpecializationRepository = doctorSpecializationRepository;
    }

    @Autowired
    public void setTypeOfAppointmentRepository(TypeOfAppointmentRepository typeOfAppointmentRepository) {
        this.typeOfAppointmentRepository = typeOfAppointmentRepository;
    }

    @Autowired
    public void setPatientRepository(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Autowired
    public void setDoctorRepository(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Autowired
    public void setDoctorScheduleRepository(DoctorScheduleRepository doctorScheduleRepository) {
        this.doctorScheduleRepository = doctorScheduleRepository;
    }

    private DoctorsSchedule initFirstHalfDay(Doctor doctor, DayOfWeek dayOfWeek) {
        return new DoctorsSchedule(doctor,
                dayOfWeek,
                LocalTime.of(8, 0, 0),
                LocalTime.of(14, 0, 0));
    }

    private DoctorsSchedule initSecondHalfDay(Doctor doctor, DayOfWeek dayOfWeek) {
        return new DoctorsSchedule(doctor,
                dayOfWeek,
                LocalTime.of(14, 0, 0),
                LocalTime.of(20, 0, 0));
    }

    private DoctorsSchedule initAllDay(Doctor doctor, DayOfWeek dayOfWeek) {
        return new DoctorsSchedule(doctor,
                dayOfWeek,
                LocalTime.of(8, 0, 0),
                LocalTime.of(18, 0, 0));
    }
}
