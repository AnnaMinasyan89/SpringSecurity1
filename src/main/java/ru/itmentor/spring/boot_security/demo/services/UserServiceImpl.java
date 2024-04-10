package ru.itmentor.spring.boot_security.demo.services;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itmentor.spring.boot_security.demo.dto.UserAddDto;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.repository.RoleRepository;
import ru.itmentor.spring.boot_security.demo.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public User readUser(int id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElse(null);
    }

    @Override
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> getUsersAndRoles() {
        return userRepository.listUsersAndRoles();
    }


    @Override
    public User findUserById(int id) {
        Optional<User> returnedUser = userRepository.findById(id);
        if (returnedUser.isEmpty()) {
            throw new IllegalArgumentException("id: " + id + " not issue");
        }
        return returnedUser.get();
    }


     @Override
   @Transactional
   public User addUser(UserAddDto userAddDto) {
         Optional<User> existingUser = userRepository.findByUsername(userAddDto.getUsername());
         if (existingUser.isPresent()) {
             throw new IllegalArgumentException("User with username " + userAddDto.getUsername() + " already exists");
         }
       List<Role> roles = roleRepository.findAllById(userAddDto.getRoles());
       User user = new User(userAddDto.getUsername(), passwordEncoder.encode(userAddDto.getPassword()), new HashSet<>());
       user.getRoles().addAll(roles);
       User savedUser = userRepository.save(user);
       return savedUser;
   }

    @Override
    @Transactional
    public User updateUser(int userId, UserAddDto userUpdateDto) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
        user.setUsername(userUpdateDto.getUsername());

        List<Role> roles = roleRepository.findAllById(userUpdateDto.getRoles());
        user.setRoles(new HashSet<>());
        user.getRoles().addAll(roles);

        return userRepository.save(user);
    }


}
