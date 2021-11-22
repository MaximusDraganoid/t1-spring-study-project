package ru.maslov.springstudyprpject.servicies;

import org.springframework.stereotype.Service;
import ru.maslov.springstudyprpject.entities.TypeOfAppointment;
import ru.maslov.springstudyprpject.repositories.TypeOfAppointmentRepository;

import java.util.Set;

@Service
public class TypeOfAppointmentService {
    private final TypeOfAppointmentRepository typeOfAppointmentRepository;

    public TypeOfAppointmentService(TypeOfAppointmentRepository typeOfAppointmentRepository) {
        this.typeOfAppointmentRepository = typeOfAppointmentRepository;
    }

    public TypeOfAppointment getTypeById(Long id) {
        //todo: сделать тест и отделный тип исключения
        return typeOfAppointmentRepository.findById(id).orElseThrow(() -> {
            throw new RuntimeException("No such type of appointment with id "
                    + id
                    + " in db");
        });
    }

    public Set<TypeOfAppointment> getTypesBySpecializationId(Long id) {
        return typeOfAppointmentRepository.findTypeOfAppointmentBySpecializationId(id);
    }
}
