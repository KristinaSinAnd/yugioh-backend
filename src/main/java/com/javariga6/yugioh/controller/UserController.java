package com.javariga6.yugioh.controller;

import com.javariga6.yugioh.model.PassResetRequest;
import com.javariga6.yugioh.model.ResetRequest;
import com.javariga6.yugioh.model.User;
import com.javariga6.yugioh.model.UserTO;
import com.javariga6.yugioh.service.UserService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity save(@RequestBody UserTO user){
        if(userService.getUserByEmail(user.getEmail()) == null) {
            userService.saveUser(user);
        } else {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public ResponseEntity delete(@RequestBody User user){
        if(userService.getUserByEmail(user.getEmail())!= null){
            userService.delete(user);
        }
        else {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
        }

    @GetMapping("/get/id/{id}")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public User getUserById(@PathVariable Long id){ return userService.getUserById(id); }

    @GetMapping("/get/email/{email}")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public User getUserByEmail(@PathVariable String email){ return userService.getUserByEmail(email); }

    @GetMapping("/delete/id/{id}")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public void deleteUserById(@PathVariable Long id){ userService.deleteById(id); }

    @GetMapping("/delete/email/{email}")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public void deleteUserByEmail(@PathVariable String email){ userService.deleteByEmail(email); }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public List<User> getAllUsers(){
        return userService.getAll();
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public void update(@RequestBody User user){
        this.userService.updateUser(user);
    }

    @PostMapping("/updatethis")
    public void updateThis(@RequestBody UserTO userTO){
        this.userService.updateThisUser(userTO);
    }

    @PostMapping("/password/requesttoken")
    public void passResetRequest(@RequestBody PassResetRequest request){
        userService.sendRecoveryToken(request);
    }

    @PostMapping("/password/reset")
    public void reset(@RequestBody ResetRequest request){
        userService.passReset(request);
    }


}
