package ru.maslov.springstudyprpject.repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import ru.maslov.springstudyprpject.entities.Role;
import ru.maslov.springstudyprpject.entities.User;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestPropertySource("classpath:unit-test-application.properties")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepositoryTest;

    @BeforeEach
    void setUp() {
        User adminUser = new User("Виталий",
                "Админович",
                "Админов",
                "great_admin",
                "admin_is_god",
                "71234567891",
                List.of(Role.ADMIN));
        userRepositoryTest.save(adminUser);
    }

    @AfterEach
    void tearDown() {
        userRepositoryTest.deleteAll();
    }

    @Test
    void canFindByLogin() {
        //given
        String searchedLogin = "great_admin";
        //when
        Optional<User> foundUser = userRepositoryTest.findByLogin(searchedLogin);
        //then
        assertThat(foundUser.isPresent()).isTrue();
        assertThat(foundUser.get().getLogin()).isEqualTo(searchedLogin);
    }

    @Test
    void canNotFindByLogin() {
        //given
        String searchedLogin = "great_admin1";
        //when
        Optional<User> foundUser = userRepositoryTest.findByLogin(searchedLogin);
        //then
        assertThat(foundUser.isPresent()).isFalse();
    }

    @Test
    void canFindByPhoneNumber() {
        //given
        String searchedPhoneNumber = "71234567891";
        //when
        List<User> users = userRepositoryTest.findAll();
        Optional<User> foundUser = userRepositoryTest.findByPhoneNumber(searchedPhoneNumber);
        //then
        assertThat(foundUser.isPresent()).isTrue();
        assertThat(foundUser.get().getPhoneNumber()).isEqualTo(searchedPhoneNumber);
    }

    @Test
    void canNotFindByPhoneNumber() {
        //given
        String searchedPhoneNumber = "79999999991";
        //when
        Optional<User> foundUser = userRepositoryTest.findByPhoneNumber(searchedPhoneNumber);
        //then
        assertThat(foundUser.isPresent()).isFalse();
    }
}