package ru.maslov.springstudyprpject.dataloaders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import ru.maslov.springstudyprpject.entities.*;
import ru.maslov.springstudyprpject.repositories.DoctorRepository;
import ru.maslov.springstudyprpject.repositories.DoctorSpecializationRepository;
import ru.maslov.springstudyprpject.repositories.PatientRepository;
import ru.maslov.springstudyprpject.repositories.TypeOfAppointmentRepository;

import java.time.Duration;
import java.util.*;

@Component
public class CommandLineAppStartUpDataLoader implements CommandLineRunner {

    private DoctorSpecializationRepository doctorSpecializationRepository;

    private TypeOfAppointmentRepository typeOfAppointmentRepository;

    private PatientRepository patientRepository;

    private DoctorRepository doctorRepository;


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
                    specializations.get(generator.nextInt(specializations.size())),
                    new LinkedList<>(),
                    new HashSet<>());

            Doctor secondDoctor = new Doctor("Петр",
                    "Петрович",
                    "Селедкин",
                    "seledkinpp",
                    "drop_the_data",
                    "79106272035",
                    specializations.get(generator.nextInt(specializations.size())),
                    new LinkedList<>(),
                    new HashSet<>());

            doctorRepository.saveAll(List.of(firstDoctor, secondDoctor));
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
}
