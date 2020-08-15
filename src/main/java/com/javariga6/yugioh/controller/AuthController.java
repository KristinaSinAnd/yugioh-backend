package com.javariga6.yugioh.controller;

import com.javariga6.yugioh.model.AuthenticationResult;
import com.javariga6.yugioh.model.Role;
import com.javariga6.yugioh.model.User;
import com.javariga6.yugioh.model.UserTO;
import com.javariga6.yugioh.service.SecurityService;
import com.javariga6.yugioh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;


@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public AuthenticationResult authenticate(@RequestBody UserTO userTO) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userTO.getEmail(), userTO.getPassword()));
        } catch (Exception e) {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "Bad credentials");
        }

        return new AuthenticationResult(securityService.generateToken(securityService.loadUserByUsername(userTO.getEmail())));
    }

    @GetMapping("/userinfo")
    public UserTO getPrincipalUser(){
        UserTO userTO = new UserTO();
        User user = userService.getUserById(securityService.getUserId());
        userTO.setEmail(user.getEmail());
        userTO.setName(user.getName());
        userTO.setSurname(user.getSurname());

        Role role = new Role();
        role.setRole(user.getRole().getRole());

        userTO.setRole(role);
        return userTO;
    }
}
