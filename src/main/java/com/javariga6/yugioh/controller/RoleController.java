package com.javariga6.yugioh.controller;

import com.javariga6.yugioh.model.CardStorage;
import com.javariga6.yugioh.model.Role;
import com.javariga6.yugioh.model.User;
import com.javariga6.yugioh.service.RoleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users/roles")
public class RoleController {
    final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    @RequestMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public void saveRole(@RequestBody Role role) {
        roleService.saveRole(role);
    }

    @GetMapping("/list")
    public List<Role> getAllRoles() {
        return roleService.getAll();
    }

    @GetMapping("/id/{id}")
    public Role getRoleById(@PathVariable Long id) {
        return roleService.getRoleById(id);
    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public void delete(@RequestBody Role role) {
        roleService.deleteRole(role);
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public void update(@RequestBody Role role) {
        this.roleService.updateRole(role);
    }
}
