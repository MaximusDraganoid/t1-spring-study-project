package ru.maslov.springstudyprpject.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Set;

@Entity
public class DoctorsSpecialization {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DoctorsSpecialization that = (DoctorsSpecialization) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(name, that.name)) return false;
        return Objects.equals(typeOfAppointmentSet, that.typeOfAppointmentSet);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (typeOfAppointmentSet != null ? typeOfAppointmentSet.hashCode() : 0);
        return result;
    }
}
