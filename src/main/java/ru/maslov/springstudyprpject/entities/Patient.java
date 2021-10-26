package ru.maslov.springstudyprpject.entities;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
public class Patient extends User{
    private String policyNumber;

    @OneToMany(mappedBy = "patient")
    private Set<Appointment> appointmentSet;

    public Patient() {}

    public Patient(String name,
                   String patronymic,
                   String surname,
                   String login,
                   String password,
                   String phoneNumber,
                   String policyNumber,
                   Set<Appointment> appointmentSet) {
        super(name, patronymic, surname, login, password, phoneNumber);
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
