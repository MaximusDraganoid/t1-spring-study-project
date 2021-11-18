package ru.maslov.springstudyprpject.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.Pattern;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Patient extends User{
    @Pattern(regexp = "[0-9]{16}")
    private String policyNumber;

    @JsonIgnore
    @OneToMany(mappedBy = "patient")
    private Set<Appointment> appointmentSet = new HashSet<>();

    public Patient() {}

    public Patient(String name,
                   String patronymic,
                   String surname,
                   String login,
                   String password,
                   String phoneNumber,
                   Collection<Role> roles,
                   String policyNumber,
                   Set<Appointment> appointmentSet) {
        super(name, patronymic, surname, login, password, phoneNumber, roles);
        this.policyNumber = policyNumber;
        this.appointmentSet = appointmentSet;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public Set<Appointment> getAppointmentSet() {
        return appointmentSet;
    }

    public void setAppointmentSet(Set<Appointment> appointmentSet) {
        this.appointmentSet = appointmentSet;
    }
}
