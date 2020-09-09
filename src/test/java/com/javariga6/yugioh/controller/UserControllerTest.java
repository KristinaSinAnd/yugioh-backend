package com.javariga6.yugioh.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javariga6.yugioh.model.*;
import com.javariga6.yugioh.repository.PassRecoveryTokenRepository;
import com.javariga6.yugioh.repository.RoleRepository;
import com.javariga6.yugioh.repository.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    PassRecoveryTokenRepository passRecoveryTokenRepository;

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
    @WithMockUser(roles = {"ADMINISTRATOR"})
    void getAllUsers() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders.get("/user/all")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        List<UserDTO> storageList = objectMapper.readValue(response, new TypeReference<List<UserDTO>>(){});
        Assert.assertEquals(storageList.size(), users.size()+1);
    }

    @Test
    @WithMockUser(roles = {"ADMINISTRATOR"})
    void update() throws Exception{
        User userInDb = users.get(RANDOM.nextInt(users.size()));
        userInDb.setName("updated_name");

        String response = mockMvc.perform(MockMvcRequestBuilders.post("/user/update")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userInDb))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        UserDTO responseUserDTO = objectMapper.readValue(response, UserDTO.class);
        Assert.assertEquals(responseUserDTO.getName(), "updated_name");

//        Bad Request
        User userWithEmptyRequiredFields = new User();
        userWithEmptyRequiredFields.setId(userInDb.getId());
        mockMvc.perform(MockMvcRequestBuilders.post("/user/update")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userWithEmptyRequiredFields))
        )
                .andDo(print())
                .andExpect(status().isBadRequest());

//        Id don't mach
        userInDb.setId(999L);
        mockMvc.perform(MockMvcRequestBuilders.post("/user/update")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userInDb))
        )
                .andDo(print())
                .andExpect(status().isBadRequest());


    }

    @Test
    void passResetRequest() throws Exception{
        User userInDb = users.get(RANDOM.nextInt(users.size()));
        PassResetRequest request = new PassResetRequest();
        request.setEmail(userInDb.getEmail());

        mockMvc.perform(MockMvcRequestBuilders.post("/user/password/requesttoken")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
                .andDo(print())
                .andExpect(status().isOk());
        Assert.assertNotNull(
                passRecoveryTokenRepository.findFirstByUser(userInDb)
        );


//        email not registered
        request.setEmail("wrong@email.com");
        mockMvc.perform(MockMvcRequestBuilders.post("/user/password/requesttoken")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    @Test
    void reset() throws Exception{
        User userInDb = users.get(RANDOM.nextInt(users.size()));
        PassResetRequest request = new PassResetRequest();
        request.setEmail(userInDb.getEmail());

        mockMvc.perform(MockMvcRequestBuilders.post("/user/password/requesttoken")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        );

        ResetRequest resetRequest = new ResetRequest();
        resetRequest.setPassword("new_password");
        resetRequest.setTokenStr(passRecoveryTokenRepository.findFirstByUser(
                userInDb
        ).get().getToken());

        mockMvc.perform(MockMvcRequestBuilders.post("/user/password/reset")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(resetRequest))
        )
                .andDo(print())
                .andExpect(status().isOk());
        String pass = userRepository.findFirstByEmail(userInDb.getEmail()).get().getPassword();

        Assert.assertTrue(
            passwordEncoder.matches("new_password", pass)
        );

//        empty password
        resetRequest.setPassword("");
        mockMvc.perform(MockMvcRequestBuilders.post("/user/password/reset")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(resetRequest))
        )
                .andDo(print())
                .andExpect(status().isBadRequest());

//        token doesn't exist
        resetRequest.setPassword("password");
        resetRequest.setTokenStr("wrong_token_string");

        mockMvc.perform(MockMvcRequestBuilders.post("/user/password/reset")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(resetRequest))
        )
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = {"ADMINISTRATOR"})
    void makeUserAdmin() throws Exception {
        User userInDb = users.get(RANDOM.nextInt(users.size()));
        Assert.assertEquals(userInDb.getRole().getRole(), "ROLE_USER");

        mockMvc.perform(MockMvcRequestBuilders.post("/user/admin")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userInDb))
        )
                .andDo(print())
                .andExpect(status().isOk());

        String newRole = userRepository.findFirstByEmail(userInDb.getEmail()).get().getRole().getRole();
        Assert.assertEquals(newRole, "ROLE_ADMINISTRATOR");

//        bad request
        User user = new User();
        user.setEmail(userInDb.getEmail());
        mockMvc.perform(MockMvcRequestBuilders.post("/user/admin")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user))
        )
                .andDo(print())
                .andExpect(status().isBadRequest());

//        user not in DB

        userInDb.setEmail("user@notindb.com");
        userInDb.setId(999L);

        mockMvc.perform(MockMvcRequestBuilders.post("/user/admin")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userInDb))
        )
                .andDo(print())
                .andExpect(status().isNotFound());
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
