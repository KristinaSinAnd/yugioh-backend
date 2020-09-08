package com.javariga6.yugioh.service;

import com.javariga6.yugioh.exceptions.BadDataInRequestException;
import com.javariga6.yugioh.exceptions.EmailInUseException;
import com.javariga6.yugioh.exceptions.ResourceNotFoundException;
import com.javariga6.yugioh.model.*;
import com.javariga6.yugioh.repository.PassRecoveryTokenRepository;
import com.javariga6.yugioh.repository.RoleRepository;
import com.javariga6.yugioh.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    final UserRepository userRepository;
    final PasswordEncoder passwordEncoder;
    final RoleRepository roleRepository;
    final SecurityService securityService;
    final EmailService emailService;
    final PassRecoveryTokenRepository passRecoveryTokenRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, SecurityService securityService, EmailService emailService, PassRecoveryTokenRepository passRecoveryTokenRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.securityService = securityService;
        this.emailService = emailService;
        this.passRecoveryTokenRepository = passRecoveryTokenRepository;
    }


    public UserDTO saveUser(User user) {
        if(userRepository.existsByEmail(user.getEmail())){
            throw new EmailInUseException();
        }
        System.out.println(user);
        user.setPassword(
                passwordEncoder.encode(user.getPassword())
        );

        Role role = roleRepository.getFirstByRole("ROLE_USER");
        if (role == null) {
            role = new Role();
            role.setRole("ROLE_USER");
            roleRepository.save(role);
        }


        user.setRole(role);
        user = userRepository.save(user);
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setName(user.getName());
        userDTO.setSurname(user.getSurname());
        userDTO.setRole(user.getRole());
        return userDTO;
    }

    @Transactional
    public User getUserById(Long id) {
        return userRepository.getOne(id);
    }

    @Transactional
    public Role getRole(Long id){
        return userRepository.getOne(id).getRole();
    }

//    public User getUserByEmail(String email) {
//        return userRepository.getFirstByEmail(email);
//    }

//    public void deleteById(Long id) {
//        userRepository.deleteById(id);
//    }

    public void delete(UserDTO user) {
        User userFromRepo = userRepository.findFirstByEmail(user.getEmail())
                .orElseThrow(ResourceNotFoundException::new);
        userRepository.delete(userFromRepo);
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

    public void updateThisUser(User updatedUser) {
        User user = userRepository.getOne(securityService.getUserId());
        user.setEmail(updatedUser.getEmail());
        user.setName(updatedUser.getName());
        user.setSurname(updatedUser.getSurname());
        if (updatedUser.getPassword() != null) {
            user.setPassword(
                    passwordEncoder.encode(
                            updatedUser.getPassword()
                    )
            );
        }
        userRepository.save(user);
    }

    public void sendRecoveryToken(PassResetRequest request) {
        User user = userRepository.findFirstByEmail(request.getEmail())
                .orElseThrow(ResourceNotFoundException::new);
        if (user == null) return;
        PassRecoveryToken token = passRecoveryTokenRepository.findFirstByUser(user);
        if (token==null) {
            token = new PassRecoveryToken();
        }
        String tokenStr = UUID.randomUUID().toString();
        token.setToken(tokenStr);
        token.setUser(user);
        passRecoveryTokenRepository.save(token);
        this.emailService.sendSimpleMessage(
                request.getEmail(),
                "Yugioh store password recovery",
                request.getHost() + "/users/password/reset/" + tokenStr
                );
    }

    public void passReset(ResetRequest request) {
        PassRecoveryToken token = passRecoveryTokenRepository.findFirstByToken(
                request.getTokenStr()
        );
        User user = token.getUser();
        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );
        userRepository.save(user);
        passRecoveryTokenRepository.delete(token);
    }

    public UserDTO makeUserAdmin(User user) {
        User userFromRepo = userRepository.findById(user.getId())
                .orElseThrow(ResourceNotFoundException::new);
        Role role = roleRepository.findFirstByRole("ROLE_ADMINISTRATOR")
                .orElse(createAdminRole("ROLE_ADMINISTRATOR"));
        userFromRepo.setRole(role);
        userRepository.save(userFromRepo);
        UserDTO userDTO = new UserDTO();
        userDTO.setRole(userFromRepo.getRole());
        userDTO.setSurname(userFromRepo.getSurname());
        userDTO.setName(userFromRepo.getName());
        userDTO.setEmail(userFromRepo.getEmail());
        return userDTO;
    }

    private Role createAdminRole(String roleName){
        Role role = new Role();
        role.setRole(roleName);
        role = roleRepository.save(role);
        return role;
    }
}
