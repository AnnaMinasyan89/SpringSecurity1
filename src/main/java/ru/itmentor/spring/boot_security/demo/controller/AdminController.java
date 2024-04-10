package ru.itmentor.spring.boot_security.demo.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.dto.UserAddDto;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.services.RoleServiceImpl;
import ru.itmentor.spring.boot_security.demo.services.UserServiceImpl;

import java.util.List;


@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminController {

    private final UserServiceImpl userService;
    private final RoleServiceImpl roleService;


    public AdminController(UserServiceImpl userService, RoleServiceImpl roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping("/users")
    public String users(Model model) {
        List<User> users = userService.getUsersAndRoles();
        model.addAttribute("users", users);
        return "admin/users";
    }


    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }


    @GetMapping("show/{id}")
    public String showUser(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.readUser(id));
        return "admin/show";
    }

    @GetMapping("/create")
    public String createUser(Model model) {
        List<Role> roles = roleService.findAllRoles();
        model.addAttribute("allRoles", roles);
        model.addAttribute("user", new UserAddDto());
        return "admin/create";
    }

    @PostMapping("/create")
    public String createUser(@ModelAttribute("user") UserAddDto user) {
        userService.addUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") int id, Model model) {
        User user = userService.findUserById(id);
        List<Role> roles = roleService.findAllRoles();
        model.addAttribute("allRoles", roles);
        model.addAttribute("user", user);
        return "admin/edit";
    }

    @PostMapping("/edit/{id}")
    public String editUser(@PathVariable("id") int id, @ModelAttribute("user") UserAddDto user){
        userService.updateUser(id, user);
        return "redirect:/admin/users";
    }



}
