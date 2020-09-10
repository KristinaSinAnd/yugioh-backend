package com.javariga6.yugioh.controller;

import com.javariga6.yugioh.exceptions.FailedLoginException;
import com.javariga6.yugioh.model.*;
import com.javariga6.yugioh.service.SecurityService;
import com.javariga6.yugioh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;


@RestController
@CrossOrigin
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public AuthenticationResult authenticate(@RequestBody UserLogin userLogin) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLogin.getEmail(), userLogin.getPassword()));
        } catch (Exception e) {
            throw new FailedLoginException(HttpStatus.UNAUTHORIZED, "Bad credentials");
        }

        return new AuthenticationResult(securityService.generateToken(securityService.loadUserByUsername(userLogin.getEmail())));
    }

    @GetMapping("/userinfo")
    @PreAuthorize("isAuthenticated()")
    public UserDTO getPrincipalUser(){
        UserDTO userDTO = new UserDTO();
        User user = userService.getUserById(securityService.getUserId());
        userDTO.setEmail(user.getEmail());
        userDTO.setName(user.getName());
        userDTO.setSurname(user.getSurname());
        userDTO.setRole(user.getRole());
        return userDTO;
    }
}
