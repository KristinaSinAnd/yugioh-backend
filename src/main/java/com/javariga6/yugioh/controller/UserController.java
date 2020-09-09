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
        System.out.println(user);
        return userService.saveUser(user);
    }

    @PostMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public UserDTO makeUserAdmin(@RequestBody @Valid User user) {
        return userService.makeUserAdmin(user);
    }


    @PostMapping("/delete")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public void delete(@RequestBody @Valid UserDTO userDTO) {
        userService.delete(userDTO);
    }

//    @GetMapping("/get/id/{id}")
//    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
//    public User getUserById(@PathVariable Long id) {
//        return userService.getUserById(id);
//    }

//    @GetMapping("/get/email/{email}")
//    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
//    public User getUserByEmail(@PathVariable String email) {
//        return userService.(email);
//    }

//    @GetMapping("/delete/id/{id}")
//    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
//    public void deleteUserById(@PathVariable Long id) {
//        userService.deleteById(id);
//    }

//    @GetMapping("/delete/email/{email}")
//    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
//    public void deleteUserByEmail(@PathVariable String email) {
//        userService.deleteByEmail(email);
//    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public List<UserDTO> getAllUsers() {
        return userService.getAll();
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public UserDTO update(@RequestBody @Valid User user) {
        return this.userService.updateUser(user);
    }

//    @PostMapping("/updatethis")
//    public void updateThis(@RequestBody User user) {
//        this.userService.updateThisUser(user);
//    }

    @PostMapping("/password/requesttoken")
    public void passResetRequest(@RequestBody PassResetRequest request) {
        userService.sendRecoveryToken(request);
    }

    @PostMapping("/password/reset")
    public void reset(@RequestBody ResetRequest request) {
        userService.passReset(request);
    }


}
