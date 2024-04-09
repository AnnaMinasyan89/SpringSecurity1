package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.model.User;

@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping({"","/"})
    public String getUserPage(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();
        if(principal instanceof User){
            User user = (User) principal;
            model.addAttribute("user", user);
        }
        return "user";
    }
}


























  /*  private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping({"","/"})
    public String getUserById(@PathVariable("id") Long id,Model model) {
        User user = userService.readUser(id);
        model.addAttribute("user", user);
        return "/user";
    }
    @PostMapping("update/{id}")
    public String updateUser(@PathVariable("id") Long id, User user, Model model){
        user.setId(id);
        userService.createOrUpdateUser(user);
        return "/user";
    }*/


