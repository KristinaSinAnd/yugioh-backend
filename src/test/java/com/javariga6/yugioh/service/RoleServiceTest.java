package com.javariga6.yugioh.service;

import com.javariga6.yugioh.exceptions.BadDataInRequestException;
import com.javariga6.yugioh.exceptions.IdInUseException;
import com.javariga6.yugioh.exceptions.ResourceNotFoundException;
import com.javariga6.yugioh.model.Role;
import com.javariga6.yugioh.repository.RoleRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @Autowired
    @InjectMocks
    private RoleService roleService;

    @Mock
    private RoleRepository roleRepository;

    Role roleWithId = new Role();
    Role roleNotExisting = new Role();
    Role roleWithoutId = new Role();

    @BeforeEach
    void setUp() {
        this.roleWithId.setId(1L);
        this.roleWithId.setRole("test_admin");

        this.roleNotExisting.setId(99L);
        this.roleNotExisting.setRole("test_admin");

        this.roleWithoutId.setRole("test_admin");
    }

    @Test
    void saveRole() {
        Mockito.when(roleRepository.save(roleWithoutId))
                .thenReturn(roleWithoutId);
        Mockito.when(roleRepository.existsById(1L))
                .thenReturn(true);

        Assert.assertEquals(roleService.saveRole(roleWithoutId), roleWithoutId);
        Assert.assertThrows(IdInUseException.class, ()->roleService.saveRole(roleWithId));
    }

    @Test
    void deleteRole() {
        Mockito.when(roleRepository.findById(99L))
                .thenReturn(Optional.empty());

        Assert.assertThrows(ResourceNotFoundException.class, ()->roleService.deleteRole(roleNotExisting));
        Assert.assertThrows(BadDataInRequestException.class, ()->roleService.deleteRole(roleWithoutId));
    }

    @Test
    void updateRole() {
        Mockito.when(roleRepository.findById(1L))
                .thenReturn(Optional.of(roleWithId));
        Mockito.when(roleRepository.findById(99L))
                .thenReturn(Optional.empty());
        Mockito.when(roleRepository.save(roleWithId))
                .thenReturn(roleWithId);

        Assert.assertEquals(roleService.updateRole(roleWithId), roleWithId);
        Assert.assertThrows(ResourceNotFoundException.class, ()->roleService.updateRole(roleNotExisting));
        Assert.assertThrows(BadDataInRequestException.class, ()->roleService.updateRole(roleWithoutId));
    }
}