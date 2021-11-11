package ru.maslov.springstudyprpject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.maslov.springstudyprpject.entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("FROM User where login = :login")
    Optional<User> findByLogin(@Param("login") String login);

    @Query("FROM User where phoneNumber = :phoneNumber")
    Optional<User> findByPhoneNumber(@Param("phoneNumber") String phoneNumber);

    Optional<User> findByName(String name);
}
