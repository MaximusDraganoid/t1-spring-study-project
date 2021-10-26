package ru.maslov.springstudyprpject.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
public class DoctorsSpecialization {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToMany
    private Set<TypeOfAppointment> typeOfAppointmentSet;

    public DoctorsSpecialization() {}

    public DoctorsSpecialization(String name, Set<TypeOfAppointment> typeOfAppointmentSet) {
        this.name = name;
        this.typeOfAppointmentSet = typeOfAppointmentSet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<TypeOfAppointment> getTypeOfAppointmentSet() {
        return typeOfAppointmentSet;
    }

    public void setTypeOfAppointmentSet(Set<TypeOfAppointment> typeOfAppointmentSet) {
        this.typeOfAppointmentSet = typeOfAppointmentSet;
    }
}
