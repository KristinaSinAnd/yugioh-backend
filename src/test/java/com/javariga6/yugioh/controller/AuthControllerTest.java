package com.javariga6.yugioh.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javariga6.yugioh.model.Role;
import com.javariga6.yugioh.model.User;
import com.javariga6.yugioh.model.UserDTO;
import com.javariga6.yugioh.model.UserLogin;
import com.javariga6.yugioh.repository.RoleRepository;
import com.javariga6.yugioh.repository.UserRepository;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public ObjectMapper objectMapper;
    private static final Random RANDOM = new Random();
    private List<User> users;

    @BeforeEach
    void setUp() {
//        create admin and 5 users in testing database
        users = new LinkedList<>();
        userRepository.deleteAll();
        roleRepository.deleteAll();
        User admin = new User();
        admin.setName("admin");
        admin.setPassword(passwordEncoder.encode("encoded_pass"));
        admin.setName("admin_name");
        admin.setEmail("admin@email.com");
        Role _adminRole = new Role();
        _adminRole.setRole("ROLE_ADMINISTRATOR");
        Role adminRole = roleRepository.save(_adminRole);
        admin.setRole(adminRole);
        userRepository.save(admin);
        Role _userRole = new Role();
        _userRole.setRole("ROLE_USER");
        Role userRole = roleRepository.save(_userRole);
        for (int i = 0; i < 5; ++i) {
            User user = new User();
            user.setName("user" + i);
            user.setPassword(passwordEncoder.encode("raw_pass"));
            user.setName("user_name" + i);
            user.setEmail("user" + i + "@email.com");
            user.setRole(userRole);
            user = userRepository.save(user);
            users.add(user);
        }
        objectMapper = new ObjectMapper();
    }

    @Test
    void authenticate() throws Exception {
        mockMvc.perform(get("/userinfo")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isForbidden());

        User userInDB = users.get(RANDOM.nextInt(users.size()));
        UserLogin login = new UserLogin();

//        failed login
        login.setEmail("wrong@emial.com");
        login.setPassword("wrong_pass");
        mockMvc.perform(post("/login")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(login))
        )
                .andDo(print())
                .andExpect(status().isUnauthorized());

        login.setEmail(userInDB.getEmail());
        login.setPassword("raw_pass");
        String response = mockMvc.perform(post("/login")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(login))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        String token = new JSONObject(response).getString("token");

//      chek if token works
        response = mockMvc.perform(get("/userinfo")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        UserDTO userInfo = objectMapper.readValue(response, UserDTO.class);

        assertEquals(userInDB.getEmail(), userInfo.getEmail());
        assertEquals(userInDB.getName(), userInfo.getName());
        assertEquals(userInDB.getSurname(), userInfo.getSurname());
        assertEquals(userInDB.getRole().getRole(), userInfo.getRole().getRole());
    }

}