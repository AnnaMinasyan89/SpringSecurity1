package ru.itmentor.spring.boot_security.demo.services;

import ru.itmentor.spring.boot_security.demo.model.Role;

import java.util.List;
import java.util.Set;

public interface RoleService {
    List<Role> findAllRoles();

    //Roman
    Set<Role> getAllRoles();
}
