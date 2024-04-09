package ru.itmentor.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.repository.RoleRepository;
import ru.itmentor.spring.boot_security.demo.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
//public class UserServiceImpl implements ru.itmentor.spring.boot_security.demo.services.UserService, UserDetailsService {
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }



   // @Transactional(readOnly = true)
    @Override
    public User readUser(int id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElse(null);
    }


    //erevi update sa klini
    public void master(int id,User updatedUser) {
        updatedUser.setId(id);
        userRepository.save(updatedUser);
    }

    @Override
    public void createUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> getUsersAndRoles() {
        return userRepository.listUsersAndRoles();
    }



    //Roman
    @Override
    public User findUserById(int id) {
        Optional<User> returnedUser = userRepository.findById(id);
        if (returnedUser.isEmpty()){
            throw new IllegalArgumentException("id: " + id + " not issue");
        }
        return returnedUser.get();
    }

    @Override
    @Transactional
    public User addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(new HashSet<>(Arrays.asList(roleRepository.getRoleById(1))));
        return userRepository.saveAndFlush(user);
    }








  /*  @Override
    public List<User> getAllUser() {
        return userRepository.getAllUsers();
    }
    @Override
    public User readUser(Long id) {
        return userRepository.readUser(id);
    }
    @Override
    public void createOrUpdateUser(User user) {
        if (user.getId()!=null){
            userRepository.updateUser(user);
        }else {
            userRepository.createUser(user);
        }

    }
    @Override
    public void deleteUser(Long id) {
        try {
            userRepository.deleteUser(id);
        } catch (NullPointerException exception){
            exception.printStackTrace();
        }
    }
    public void updateUser(User user){
        userRepository.updateUser(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty())
            throw new UsernameNotFoundException("User not found");

        return user.get();
    }*/
}
