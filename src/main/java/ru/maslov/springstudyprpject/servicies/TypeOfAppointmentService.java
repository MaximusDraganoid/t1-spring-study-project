package ru.maslov.springstudyprpject.servicies;

import org.springframework.stereotype.Service;
import ru.maslov.springstudyprpject.entities.TypeOfAppointment;
import ru.maslov.springstudyprpject.exceptions.TypeOfAppointmentNotFoundException;
import ru.maslov.springstudyprpject.repositories.TypeOfAppointmentRepository;

import java.util.Set;

@Service
public class TypeOfAppointmentService {
    private final TypeOfAppointmentRepository typeOfAppointmentRepository;

    public TypeOfAppointmentService(TypeOfAppointmentRepository typeOfAppointmentRepository) {
        this.typeOfAppointmentRepository = typeOfAppointmentRepository;
    }

    public TypeOfAppointment findById(Long id) {
        //todo: сделать тест и отделный тип исключения
        return typeOfAppointmentRepository.findById(id).orElseThrow(() -> {
            throw new TypeOfAppointmentNotFoundException("type of appointment with id " +
                    id +
                    " not found");
        });
    }

    public Set<TypeOfAppointment> getTypesBySpecializationId(Long id) {
        return typeOfAppointmentRepository.findTypeOfAppointmentBySpecializationId(id);
    }
}
