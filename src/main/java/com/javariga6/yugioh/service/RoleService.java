package com.javariga6.yugioh.service;

import com.javariga6.yugioh.model.Role;
import com.javariga6.yugioh.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public void saveRole(Role role){
        roleRepository.save(role);
    }

    public Role getRoleById(Long id){
        return roleRepository.getOne(id);
    }

    public List<Role> getAll(){
        return roleRepository.findAll();
    }
}
