package com.javariga6.yugioh.service;

import com.javariga6.yugioh.model.Role;
import com.javariga6.yugioh.model.User;
import com.javariga6.yugioh.model.UserTO;
import com.javariga6.yugioh.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class UserService {
    final UserRepository userRepository;
    final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void saveUser(User user){
        userRepository.save(user);
    }


    public void saveUser(UserTO userTo){
        User user = new User();
        user.setEmail(userTo.getEmail());
        user.setName(userTo.getName());
        user.setSurname(userTo.getSurname());
        user.setPassword(
                passwordEncoder.encode(userTo.getPassword())
        );
        Role role = new Role();
        role.setId(2L);
        role.setRole("ROLE_USER");
        user.setRole(role);
        userRepository.save(user);
    }

    public User getUserById(Long id){
        return userRepository.getOne(id);
    }

    public User getUserByEmail(String email){
        return userRepository.getFirstByEmail(email);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public void deleteByEmail(String email) {
        User userToDelete = userRepository.getFirstByEmail(email);
        userRepository.delete(userToDelete);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public void updateUser(User user) {
        System.out.println(user);
        User userFromRepo = userRepository.getOne(user.getId());
        userFromRepo.setName(user.getName());
        userFromRepo.setEmail(user.getEmail());
        userFromRepo.setSurname(user.getSurname());
        userRepository.save(userFromRepo);
    }
}
