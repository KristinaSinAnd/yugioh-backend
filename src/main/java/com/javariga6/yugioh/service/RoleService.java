package com.javariga6.yugioh.service;

import com.javariga6.yugioh.exceptions.BadDataInRequestException;
import com.javariga6.yugioh.exceptions.IdInUseException;
import com.javariga6.yugioh.exceptions.ResourceNotFoundException;
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

    public Role saveRole(Role role) {
        if(role.getId() != null) {
            if (roleRepository.existsById(role.getId())){
                throw new IdInUseException();
            }
        }
        return roleRepository.save(role);
    }

    public Role getRoleById(Long id) {
        return roleRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    public List<Role> getAll() {
        return roleRepository.findAll();
    }

    public void deleteRole(Role role) {
        if (role.getId() == null){
            throw new BadDataInRequestException();
        }
        Role roleFromRepo = roleRepository.findById(role.getId())
                .orElseThrow(ResourceNotFoundException::new);
        roleRepository.delete(roleFromRepo);
    }

    public Role updateRole(Role role) {
        Role roleFromRepo = roleRepository.findById(role.getId())
                .orElseThrow(ResourceNotFoundException::new);
        roleFromRepo.setRole(role.getRole());
        return roleRepository.save(roleFromRepo);
    }

}
