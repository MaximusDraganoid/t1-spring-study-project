package ru.maslov.springstudyprpject.security.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.maslov.springstudyprpject.entities.User;
import ru.maslov.springstudyprpject.servicies.UserService;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByName(username).orElseThrow(() -> {
            throw new RuntimeException("User with username " + username + " not found");
        });
        
        return null;
    }
}
