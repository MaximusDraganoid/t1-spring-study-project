package ru.maslov.springstudyprpject.entities;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Pattern(regexp = "[А-Я][а-я-]+")
    @Length(min = 2, max = 40)
    private String name;

    @NotNull
    @Pattern(regexp = "[А-Я][а-я-]+")
    @Length(min = 2, max = 40)
    private String patronymic;

    @NotNull
    @Pattern(regexp = "[А-Я][а-я-]+")
    @Length(min = 2, max = 40)
    private String surname;

    @NotNull
    @Pattern(regexp = "[a-zA-Z][A-Za-z1-9-_]*")
    @Length(min = 3, max = 14)
    private String login;

    @NotNull
    private String password;

    @NotNull
    @Pattern(regexp = "[7|8][0-9]{10}")
    private String phoneNumber;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles")
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Collection<Role> roles = new ArrayList<>();

    public User() {
    }

    public User(String name,
                String patronymic,
                String surname,
                String login,
                String password,
                String phoneNumber,
                Collection<Role> roles) {
        this.name = name;
        this.patronymic = patronymic;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.roles = roles;
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

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!Objects.equals(id, user.id)) return false;
        if (!Objects.equals(name, user.name)) return false;
        if (!Objects.equals(patronymic, user.patronymic)) return false;
        if (!Objects.equals(surname, user.surname)) return false;
        if (!Objects.equals(login, user.login)) return false;
        if (!Objects.equals(password, user.password)) return false;
        return Objects.equals(phoneNumber, user.phoneNumber);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (patronymic != null ? patronymic.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        return result;
    }
}
