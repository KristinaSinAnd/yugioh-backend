package com.javariga6.yugioh.service;

import com.javariga6.yugioh.model.Role;
import com.javariga6.yugioh.model.User;
import com.javariga6.yugioh.model.UserTO;
import com.javariga6.yugioh.repository.RoleRepository;
import com.javariga6.yugioh.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    final UserRepository userRepository;
    final PasswordEncoder passwordEncoder;
    final RoleRepository roleRepository;
    final SecurityService securityService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, SecurityService securityService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.securityService = securityService;
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


        Role role = roleRepository.getFirstByRole("ROLE_ADMINISTRATOR");    //TODO Change to user for prod
        if(role == null){
            role = new Role();
            role.setRole("ROLE_ADMINISTRATOR");
            roleRepository.save(role);
        }

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
        User userFromRepo = userRepository.getOne(user.getId());
        userFromRepo.setName(user.getName());
        userFromRepo.setEmail(user.getEmail());
        userFromRepo.setSurname(user.getSurname());
        userRepository.save(userFromRepo);
    }

    public void updateThisUser(UserTO userTO) {
        User user = userRepository.getOne(securityService.getUserId());
        user.setEmail(userTO.getEmail());
        user.setName(userTO.getName());
        user.setSurname(userTO.getSurname());
        if (userTO.getPassword()!=null){
            user.setPassword(
                    passwordEncoder.encode(
                            userTO.getPassword()
                    )
            );
        }
        userRepository.save(user);
    }
}
