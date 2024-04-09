package ru.itmentor.spring.boot_security.demo.services;

import ru.itmentor.spring.boot_security.demo.model.User;

import javax.transaction.Transactional;
import java.util.List;

public interface UserService {
   /* List<User> getAllUser();
    User readUser(Long id);
    void createOrUpdateUser(User user);
    void deleteUser(Long id);*/

    void createUser(User user);

    User readUser(int id);



    void deleteUser(int id);

    List<User> getUsersAndRoles();

    //Roman
    User findUserById(int id);

    @Transactional
    User addUser(User user);
}