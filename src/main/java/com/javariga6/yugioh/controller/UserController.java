package com.javariga6.yugioh.controller;

import com.javariga6.yugioh.model.User;
import com.javariga6.yugioh.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public void save(@RequestBody User user){ userService.saveUser(user); }

    @PostMapping("/delete")
    public void delete(@RequestBody User user){ userService.delete(user); }

    @GetMapping("/get/id/{id}")
    public User getUserById(@PathVariable Long id){ return userService.getUserById(id); }

    @GetMapping("/get/email/{email}")
    public User getUserByEmail(@PathVariable String email){ return userService.getUserByEmail(email); }

    @GetMapping("/delete/id/{id}")
    public void deleteUserById(@PathVariable Long id){ userService.deleteById(id); }

    @GetMapping("/delete/email/{email}")
    public void deleteUserByEmail(@PathVariable String email){ userService.deleteByEmail(email); }

}
