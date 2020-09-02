package com.javariga6.yugioh.controller;

import com.javariga6.yugioh.model.CardStorage;
import com.javariga6.yugioh.model.Role;
import com.javariga6.yugioh.model.User;
import com.javariga6.yugioh.service.RoleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public Role saveRole(@RequestBody @Valid Role role) {
        return roleService.saveRole(role);
    }

    @GetMapping("/list")
    public List<Role> getAllRoles() {
        return roleService.getAll();
    }

//    @GetMapping("/id/{id}")
//    public Role getRoleById(@PathVariable Long id) {
//        return roleService.getRoleById(id);
//    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public void delete(@RequestBody @Valid Role role) {
        roleService.deleteRole(role);
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public Role update(@RequestBody @Valid Role role) {
        return roleService.updateRole(role);
    }
}
