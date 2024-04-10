package ru.itmentor.spring.boot_security.demo.services;

import ru.itmentor.spring.boot_security.demo.dto.UserAddDto;
import ru.itmentor.spring.boot_security.demo.model.User;

import javax.transaction.Transactional;
import java.util.List;

public interface UserService {
    User readUser(int id);

    void deleteUser(int id);

    List<User> getUsersAndRoles();

    User findUserById(int id);

    @Transactional
    User addUser(UserAddDto user);

    @Transactional
    User updateUser(int userId, UserAddDto userUpdateDto);
}
