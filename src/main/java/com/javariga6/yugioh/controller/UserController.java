package com.javariga6.yugioh.controller;

import com.javariga6.yugioh.model.*;
import com.javariga6.yugioh.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @RequestMapping("/register")
    public UserDTO save(@RequestBody @Valid User user) {
        return userService.saveUser(user);
    }

    @PostMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public UserDTO makeUserAdmin(@RequestBody @Valid UserDTO userDTO) {
        return userService.makeUserAdmin(userDTO);
    }


    @PostMapping("/delete")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public void delete(@RequestBody @Valid UserDTO userDTO) {
        userService.delete(userDTO);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public List<UserDTO> getAllUsers() {
        return userService.getAll();
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public UserDTO update(@RequestBody @Valid UserDTO userDTO) {
        return this.userService.updateUser(userDTO);
    }

    @PostMapping("/password/requesttoken")
    public void passResetRequest(@RequestBody @Valid PassResetRequest request) {
        userService.sendRecoveryToken(request);
    }

    @PostMapping("/password/reset")
    public void reset(@RequestBody @Valid ResetRequest request) {
        userService.passReset(request);
    }


}
