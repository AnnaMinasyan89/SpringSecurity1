package ru.itmentor.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.itmentor.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
/*
    List<User> getAllUsers();

    void createUser(User user);
    User readUser(Long id);

    void updateUser(User user);

    void  deleteUser(Long id);

    Optional<User> findByUsername(String username);

 */

    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u JOIN FETCH u.roles r")
    List<User> listUsersAndRoles();

}