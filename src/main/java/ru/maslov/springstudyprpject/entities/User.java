package ru.maslov.springstudyprpject.entities;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Pattern(regexp = "[А-Я][а-я-]+")
    @Length(min = 2, max = 40)
    private String name;

    @Pattern(regexp = "[А-Я][а-я-]+")
    @Length(min = 2, max = 40)
    private String patronymic;

    @Pattern(regexp = "[А-Я][а-я-]+")
    @Length(min = 2, max = 40)
    private String surname;

    @Pattern(regexp = "[a-zA-Z][A-Za-z1-9-_]*")
    @Length(min = 3, max = 14)
    private String login;

    @Pattern(regexp = "[A-Za-z1-9-_]*")
    @Length(min = 3, max = 15)
    private String password;

    @Pattern(regexp = "[7|8][0-9]{10}")
    private String phoneNumber;

    public User() {
    }

    public User(String name,
                String patronymic,
                String surname,
                String login,
                String password,
                String phoneNumber) {
        this.name = name;
        this.patronymic = patronymic;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.phoneNumber = phoneNumber;
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

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
