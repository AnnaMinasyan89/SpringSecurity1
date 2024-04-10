package ru.itmentor.spring.boot_security.demo.services;


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


    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }


    // @Transactional(readOnly = true)
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

    /*   @Override
   @Transactional
   public User addUser(UserAddDto userAddDto) {
       List<Role> roles = roleRepository.findAllById(userAddDto.getRoles());
       User user = new User(userAddDto.getUsername(), userAddDto.getPassword(), new HashSet<>());
       user.getRoles().addAll(roles);
       User savedUser = userRepository.save(user);
       return savedUser;
   }*/

     @Override
   @Transactional
   public User addUser(UserAddDto userAddDto) {
         Optional<User> existingUser = userRepository.findByUsername(userAddDto.getUsername());
         if (existingUser.isPresent()) {
             throw new IllegalArgumentException("User with username " + userAddDto.getUsername() + " already exists");
         }
       List<Role> roles = roleRepository.findAllById(userAddDto.getRoles());
       User user = new User(userAddDto.getUsername(), userAddDto.getPassword(), new HashSet<>());
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

  /*  @Override
    @Transactional
    public User addUser(UserAddDto userAddDto) {
        // Проверяем, существует ли уже пользователь с таким именем
        Optional<User> existingUser = userRepository.findByUsername(userAddDto.getUsername());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("User with username " + userAddDto.getUsername() + " already exists");
        }

        // Получаем все роли из репозитория
        List<Role> allRoles = roleRepository.findAll();

        // Создаем пустой набор для ролей пользователя
        Set<Role> userRoles = new HashSet<>();

        // Перебираем роли из DTO и добавляем их в набор
        for (Long roleId : userAddDto.getRoles()) {
            Role role = allRoles.stream()
                    .filter(r -> r.getId().equals(roleId))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Role with id " + roleId + " not found"));
            userRoles.add(role);
        }

        // Создаем пользователя с указанными ролями
        User user = new User(userAddDto.getUsername(), userAddDto.getPassword(), userRoles);

        // Сохраняем пользователя
        return userRepository.save(user);
    }
*/

/*    @Override
    @Transactional
    public User updateUser(int userId, UserAddDto userUpdateDto) {
        // Получаем пользователя из базы данных
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        // Обновляем данные пользователя
        user.setUsername(userUpdateDto.getUsername());
      //  user.setPassword(userUpdateDto.getPassword());

        // Получаем все роли из репозитория
        List<Role> allRoles = roleRepository.findAll();

        // Создаем пустой набор для ролей пользователя
        Set<Role> userRoles = new HashSet<>();

        // Перебираем роли из DTO и добавляем их в набор
        for (Long roleId : userUpdateDto.getRoles()) {
            Role role = allRoles.stream()
                    .filter(r -> r.getId().equals(roleId))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Role with id " + roleId + " not found"));
            userRoles.add(role);
        }

        // Обновляем роли пользователя
        user.setRoles(userRoles);

        // Сохраняем обновленного пользователя
        return userRepository.save(user);
    }*/

}
