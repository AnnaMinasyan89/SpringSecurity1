package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.services.RoleServiceImpl;
import ru.itmentor.spring.boot_security.demo.services.UserService;
import ru.itmentor.spring.boot_security.demo.services.UserServiceImpl;

import java.util.List;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminController {

    private final UserServiceImpl userService;
    private final RoleServiceImpl roleService;

    private final PasswordEncoder passwordEncoder;

    public AdminController(UserServiceImpl userService, RoleServiceImpl roleService,PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    //+++++++++++++++++++++
    @GetMapping("/users")
    public String showUserList(Model model) {
        List<User> users = userService.getUsersAndRoles();
        model.addAttribute("users", users);
        return "admin/all";
    }


    //++++++++++++++
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }


    //+++++++++++++
    @GetMapping( "/{id}")
    public String readUser(@PathVariable("id")int id , Model model) {
        model.addAttribute("user",userService.readUser(id));
      //  return "user";
        return "admin/show";
    }


    //+++++++++++++++++++     save-----
    @GetMapping("/createUser")

    public String newForm(Model model) {
        List<Role> roles = roleService.findAllRoles();
        model.addAttribute("allRoles", roles);
        model.addAttribute("user", new User());
        return "admin/new";
    }

    @PostMapping("/create/new")
  // @GetMapping("/create/new")
    public String createUser(@ModelAttribute("user") User user) {
        userService.addUser(user);
        return "redirect:/admin/users";
    }



//+++++++++             role-----,  edit-----------
    @GetMapping("/editUser/{id}")
    public String showEditForm(@PathVariable("id") int id, Model model) {

        User user =userService.findUserById(id);
        List<Role> roles = roleService.findAllRoles();
     //   List<Role> roles =  roleService.getAllRoles();
        model.addAttribute("allRoles", roles);
        model.addAttribute("user", user);

        return "admin/edit";

    }

    @PostMapping("/edit/{id}")
    public String editUser(@PathVariable("id") int id, User user,
                             BindingResult bindingResult){
        /*if (bindingResult.hasErrors()){
            user.setId(id);
            return "admin/edit";
        }else {
            userService.addUser(user);
            return "redirect:/admin/users";
        }*/

        return "redirect:/admin/users";
    }


/*  @PostMapping("/edit/{id}")
    public String master(@ModelAttribute ("user") User user, @PathVariable("id") int id) {
       userService.master(id,user);
        return "redirect:/admin/users";
    }*/




    //L
    /*@PostMapping(value = "/create")
    public String createUser(@ModelAttribute("user") User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.createUser(user);
        return "redirect:/admin/users";
    }*/

}
