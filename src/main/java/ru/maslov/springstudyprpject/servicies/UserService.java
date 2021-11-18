package ru.maslov.springstudyprpject.servicies;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.maslov.springstudyprpject.entities.User;
import ru.maslov.springstudyprpject.exceptions.UserAlreadyExistsException;
import ru.maslov.springstudyprpject.repositories.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public Optional<User> findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public User save(User user) {
        if (userRepository.findByLogin(user.getLogin()).isPresent()) {
            throw new UserAlreadyExistsException("User with login " + user.getLogin() + " already exists in system");
        }

        if (userRepository.findByPhoneNumber(user.getPhoneNumber()).isPresent()) {
            throw new UserAlreadyExistsException("User with phone number " + user.getPhoneNumber() + " already exists in system");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
