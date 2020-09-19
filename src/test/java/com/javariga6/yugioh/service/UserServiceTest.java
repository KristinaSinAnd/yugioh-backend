package com.javariga6.yugioh.service;

import com.javariga6.yugioh.exceptions.BadDataInRequestException;
import com.javariga6.yugioh.exceptions.EmailInUseException;
import com.javariga6.yugioh.exceptions.ResourceNotFoundException;
import com.javariga6.yugioh.model.Role;
import com.javariga6.yugioh.model.User;
import com.javariga6.yugioh.model.UserDTO;
import com.javariga6.yugioh.repository.RoleRepository;
import com.javariga6.yugioh.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Autowired
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RoleRepository roleRepository;

    private UserDTO userDTO;
    private Role roleUser = new Role("ROLE_USER");
    private Role roleAdmin = new Role("ROLE_ADMINISTRATOR");

    @BeforeEach
    void setUp() {
        userDTO = new UserDTO();
        userDTO.setEmail("test@email.com");
        userDTO.setName("test_name");
        userDTO.setSurname("test_surname");
        userDTO.setId(1L);
        userDTO.setRole(roleUser);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void saveUser() {
        User user = new User();
        user.setEmail("test@user.com");
        user.setPassword("test_pass");
        user.setName("test_name");
        Role roleUser = new Role();
        roleUser.setId(1L);
        roleUser.setRole("ROLE_USER");

        Mockito.when(userRepository.save(user))
                .thenReturn(user);
        Mockito.when(userRepository.existsByEmail("emailInUse@mail.com"))
                .thenReturn(true);
        Mockito.when(userRepository.existsByEmail("test@user.com"))
                .thenReturn(false);
        Mockito.when(passwordEncoder.encode("test_pass"))
                .thenReturn("encoded_pass");
        Mockito.when(roleRepository.getFirstByRole("ROLE_USER"))
                .thenReturn(roleUser);

//        Valid user save
        assertNotNull(userService.saveUser(user));

//        Email in use
        user.setEmail("emailInUse@mail.com");
        assertThrows(EmailInUseException.class, () ->userService.saveUser(user));

    }

    @Test
    void delete() {
        Mockito.when(userRepository.findFirstByEmail("test@email.com"))
                .thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, ()->userService.delete(userDTO));
    }

    @Test
    void updateUser() {
        Mockito.when(userRepository.findFirstByEmail("test@email.com"))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, ()->userService.updateUser(userDTO));

        User mockUserFromRepo = new User();
        mockUserFromRepo.setEmail(userDTO.getEmail());
        mockUserFromRepo.setId(99L);

        Mockito.when(userRepository.findFirstByEmail("test@email.com"))
                .thenReturn(Optional.of(mockUserFromRepo));

//        id does not mach email
        assertThrows(BadDataInRequestException.class, ()-> userService.updateUser(userDTO));

    }

    @Test
    void makeUserAdmin() {
        User user = new User();
        user.setEmail("test@email.com");
        user.setName("test_name");
        user.setSurname("test_surname");
        user.setId(1L);
        user.setRole(roleUser);
        Mockito.when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));
        Mockito.when(roleRepository.findFirstByRole("ROLE_ADMINISTRATOR"))
                .thenReturn(Optional.of(roleAdmin));

        assertEquals(userService.makeUserAdmin(userDTO).getRole().getRole(), "ROLE_ADMINISTRATOR");

        Mockito.when(userRepository.findById(1L))
                .thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, ()->userService.makeUserAdmin(userDTO));
    }
}