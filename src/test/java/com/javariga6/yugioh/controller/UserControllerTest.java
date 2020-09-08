package com.javariga6.yugioh.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javariga6.yugioh.model.Role;
import com.javariga6.yugioh.model.User;
import com.javariga6.yugioh.repository.RoleRepository;
import com.javariga6.yugioh.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
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

    private User admin;
    private List<User> users;
    private Role adminRole;
    private Role userRole;

    @BeforeEach
    void setUp() {
//        create admin and 5 users in testing database
        users = new LinkedList<>();
        userRepository.deleteAll();
        roleRepository.deleteAll();
        admin = new User();
        admin.setName("admin");
        admin.setPassword(passwordEncoder.encode("encoded_pass"));
        admin.setName("admin_name");
        admin.setEmail("admin@email.com");
        Role _adminRole = new Role();
        _adminRole.setRole("ROLE_ADMINISTRATOR");
        adminRole = roleRepository.save(_adminRole);
        admin.setRole(adminRole);
        userRepository.save(admin);
        Role _userRole = new Role();
        _userRole.setRole("ROLE_USER");
        userRole = roleRepository.save(_userRole);
        for (int i=0; i<5; ++i){
            User user = new User();
            user.setName("user"+i);
            user.setPassword(passwordEncoder.encode("encoded_pass"+i));
            user.setName("user_name"+i);
            user.setEmail("user"+i+"@email.com");
            user.setRole(userRole);
            user = userRepository.save(user);
            users.add(user);
        }
        objectMapper = new ObjectMapper();

    }

    @Test
    void save() throws Exception{
        User newUser = new User();
        newUser.setEmail("new_user@email.com");
        newUser.setName("new_user_name");
        newUser.setPassword("new_user_password");
        mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser))
        )
                .andDo(print())
                .andExpect(status().isOk());

//       email not valid
        newUser.setEmail("not_a_valid_email");
        mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser))
        )
                .andDo(print())
                .andExpect(status().isBadRequest());

        //       email already registered
        newUser.setEmail("new_user@email.com");
        mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser))
        )
                .andDo(print())
                .andExpect(status().isConflict());

//        no password set
        newUser.setEmail("valid@email.com");
        newUser.setPassword("");
        mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser))
        )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = {"ADMINISTRATOR"})
    void delete() throws Exception {
        User userInDB = users.get(RANDOM.nextInt(users.size()));

        mockMvc.perform(MockMvcRequestBuilders.post("/user/delete")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userInDB))
        )
                .andDo(print())
                .andExpect(status().isOk());

//        User nonexistent
        mockMvc.perform(MockMvcRequestBuilders.post("/user/delete")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userInDB))
        )
                .andDo(print())
                .andExpect(status().isNotFound());

        //        Bad request
        User user = new User();
        mockMvc.perform(MockMvcRequestBuilders.post("/user/delete")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user))
        )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void getUserById() {
    }

    @Test
    void getUserByEmail() {
    }

    @Test
    void getAllUsers() {
    }

    @Test
    void update() {
    }

    @Test
    void updateThis() {
    }

    @Test
    void passResetRequest() {
    }

    @Test
    void reset() {
    }

    @Test
    @WithMockUser(roles = {"ADMINISTRATOR"})
    void makeUserAdmin() {


    }

//    @Test
//    public void register() throws Exception {
//        JSONObject json = new JSONObject()
//                .put("email", "epasts@mail.com")
//                .put("surname", "surname")
//                .put("password", "parole")
//                .put("name", "user");
//        JSONObject login = new JSONObject()
//                .put("email", "admin@admin.com")
//                .put("password", "admin");
//
//        String jsonToken = mockMvc.perform(post("/login")
//                .contentType(APPLICATION_JSON)
//                .accept(APPLICATION_JSON)
//                .content(login.toString())
//        )
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andReturn().getResponse().getContentAsString();
//
//        String token = new JSONObject(jsonToken).getString("token");
//
//
//
//        mockMvc.perform(post("/user/delete")
//                .contentType(APPLICATION_JSON)
//                .accept(APPLICATION_JSON)
//                .header("Authorization", "Bearer " + token)
//                .content(json.toString())
//        )
//                .andDo(print())
//                .andExpect(status().isOk());
//
//        mockMvc.perform(post("/user/register")
//                        .contentType(APPLICATION_JSON)
//                        .accept(APPLICATION_JSON)
//        .content(json.toString())
//        )
//                .andDo(print())
//                .andExpect(status().isOk());
//
//        mockMvc.perform(post("/user/register")
//                .contentType(APPLICATION_JSON)
//                .accept(APPLICATION_JSON)
//                .content(json.toString())
//        )
//                .andDo(print())
//                .andExpect(status().isBadRequest());
//
//    }
}
