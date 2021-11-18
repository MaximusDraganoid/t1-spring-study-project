package ru.maslov.springstudyprpject.servicies;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.maslov.springstudyprpject.entities.Role;
import ru.maslov.springstudyprpject.entities.User;
import ru.maslov.springstudyprpject.exceptions.UserAlreadyExistsException;
import ru.maslov.springstudyprpject.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private UserService userServiceTest;

    @BeforeEach
    void setUp() {
        userServiceTest = new UserService(passwordEncoder, userRepository);
    }

    @Test
    void canFindByLogin() {
        //given
        String sampleLogin = "sample_login";
        //when
        userServiceTest.findByLogin(sampleLogin);
        //then
        verify(userRepository).findByLogin(sampleLogin);
    }

    @Test
    void canSaveUser() {
        //given
        User user = new User("Виталий",
                "Админович",
                "Админов",
                "great_admin",
                passwordEncoder.encode("admin_is_god"),
                "79999999999",
                List.of(Role.ADMIN));
        //when
        userServiceTest.save(user);
        ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);
        //then
        verify(userRepository).findByPhoneNumber(user.getPhoneNumber());
        verify(userRepository).findByLogin(user.getLogin());
        verify(userRepository).save(argumentCaptor.capture());
        verify(passwordEncoder).encode(user.getPassword());
        assertThat(argumentCaptor.getValue().getLogin()).isEqualTo(user.getLogin());
    }

    @Test
    void canNotSaveUserBecauseUserWithSameLoginExists() {
        //given
        User user = new User("Виталий",
                "Админович",
                "Админов",
                "great_admin",
                passwordEncoder.encode("admin_is_god"),
                "79999999999",
                List.of(Role.ADMIN));
        //when
        Mockito
                .doReturn(Optional.of(new User()))
                .when(userRepository)
                .findByLogin(user.getLogin());
        //then
        assertThatThrownBy(() -> userServiceTest.save(user))
                .hasMessage("User with login " + user.getLogin() + " already exists in system")
                .isInstanceOf(UserAlreadyExistsException.class);
        verify(userRepository, never()).save(user);
        verify(passwordEncoder, never()).encode(user.getPassword());
    }

    @Test
    void canNotSaveUserBecauseUserWithSamePhoneNumberExists() {
        //given
        User user = new User("Виталий",
                "Админович",
                "Админов",
                "great_admin",
                passwordEncoder.encode("admin_is_god"),
                "79999999999",
                List.of(Role.ADMIN));
        //when
        Mockito
                .doReturn(Optional.of(new User()))
                .when(userRepository)
                .findByPhoneNumber(user.getPhoneNumber());
        //then
        assertThatThrownBy(() -> userServiceTest.save(user))
                .hasMessage("User with phone number " + user.getPhoneNumber() + " already exists in system")
                .isInstanceOf(UserAlreadyExistsException.class);
        verify(userRepository, never()).save(user);
        verify(passwordEncoder, never()).encode(user.getPassword());
    }
}