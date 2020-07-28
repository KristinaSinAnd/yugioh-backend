package com.javariga6.yugioh.controller;

import com.javariga6.yugioh.model.Role;
import com.javariga6.yugioh.service.RoleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {
    final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public void saveRole(@RequestBody Role role){
        roleService.saveRole(role);
    }

    @GetMapping
    public List<Role> getAllRoles(){
        return roleService.getAll();
    }

    @GetMapping("/id/{id}")
    public Role getRoleById(@PathVariable Long id){
        return roleService.getRoleById(id);
    }
}
