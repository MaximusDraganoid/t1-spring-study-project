package ru.maslov.springstudyprpject.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
public class Doctor extends User {
    @OneToOne
    @NotNull
    private DoctorsSpecialization specialization;

    @OneToMany(mappedBy = "doctor")
    private List<DoctorsSchedule> scheduleList;

    @OneToMany(mappedBy = "doctor")
    @JsonIgnore
    private Set<Appointment> appointments;

    public Doctor() {}

    public Doctor(String name,
                  String patronymic,
                  String surname,
                  String login,
                  String password,
                  String phoneNumber,
                  Collection<Role> roles,
                  DoctorsSpecialization specialization,
                  List<DoctorsSchedule> scheduleList,
                  Set<Appointment> appointments) {
        super(name, patronymic, surname, login, password, phoneNumber, roles);
        this.specialization = specialization;
        this.scheduleList = scheduleList;
        this.appointments = appointments;
    }

    public DoctorsSpecialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(DoctorsSpecialization specialization) {
        this.specialization = specialization;
    }

    public List<DoctorsSchedule> getScheduleList() {
        return scheduleList;
    }

    public void setScheduleList(List<DoctorsSchedule> scheduleList) {
        this.scheduleList = scheduleList;
    }

    public Set<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(Set<Appointment> appointments) {
        this.appointments = appointments;
    }


}
