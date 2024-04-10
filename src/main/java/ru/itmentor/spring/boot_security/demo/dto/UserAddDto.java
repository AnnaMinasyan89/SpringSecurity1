package ru.itmentor.spring.boot_security.demo.dto;

import java.util.List;
import java.util.Set;

public class UserAddDto {

    private String username;
    private String password;
    private List<Long> roles;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Long> getRoles() {
        return roles;
    }

    public void setRoles(List<Long> roles) {
        this.roles = roles;
    }

}
