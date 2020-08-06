package com.javariga6.yugioh.controller;

import com.javariga6.yugioh.model.User;
import com.javariga6.yugioh.model.UserTO;
import com.javariga6.yugioh.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    public void save(@RequestBody UserTO user){ userService.saveUser(user); }

    @PostMapping("/delete")
    public void delete(@RequestBody User user){ userService.delete(user); }

    @GetMapping("/get/id/{id}")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public User getUserById(@PathVariable Long id){ return userService.getUserById(id); }

    @GetMapping("/get/email/{email}")
    public User getUserByEmail(@PathVariable String email){ return userService.getUserByEmail(email); }

    @GetMapping("/delete/id/{id}")
    public void deleteUserById(@PathVariable Long id){ userService.deleteById(id); }

    @GetMapping("/delete/email/{email}")
    public void deleteUserByEmail(@PathVariable String email){ userService.deleteByEmail(email); }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public List<User> getAllUsers(){
        return userService.getAll();
    }

    @PostMapping("/update")
    public void update(@RequestBody User user){
        this.userService.updateUser(user);
    }




}
