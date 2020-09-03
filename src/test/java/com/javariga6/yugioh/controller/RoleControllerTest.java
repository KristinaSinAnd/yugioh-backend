package com.javariga6.yugioh.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javariga6.yugioh.model.*;
import com.javariga6.yugioh.repository.RoleRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Random;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class RoleControllerTest {

    private static final Random RANDOM = new Random();
    private final String testString = "String for testing";
    public ObjectMapper objectMapper;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private MockMvc mockMvc;
    private List<Role> rolesInDB;

    @BeforeEach
    public void before() {
        roleRepository.deleteAll();
        for (int i = 0; i < 5; ++i) {
            Role role = new Role();
            role.setRole("role_" + i);
            roleRepository.save(role);
        }
        this.rolesInDB = roleRepository.findAll();
        this.objectMapper = new ObjectMapper();
    }

    @Test
    @WithMockUser(roles = {"ADMINISTRATOR"})
    void saveRole() throws Exception {
        Role newRole = new Role();
        newRole.setRole("role_new");

        String response = mockMvc.perform(MockMvcRequestBuilders.post("/users/roles/create")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newRole))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Role responseRole = objectMapper.readValue(response, Role.class);
        newRole.setId(responseRole.getId());
        Assert.assertEquals(newRole.toString(), responseRole.toString());

        //        Bad request
        Role emptyRole = new Role();
        mockMvc.perform(MockMvcRequestBuilders.post("/users/roles/create")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(emptyRole))
        )
                .andDo(print())
                .andExpect(status().isBadRequest());

//        Role with existing id
        mockMvc.perform(MockMvcRequestBuilders.post("/users/roles/create")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(responseRole))
        )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }


    @Test
    @WithMockUser(roles = {"ADMINISTRATOR"})
    void getAllRoles() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders.get("/users/roles/list")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        List<Role> roleList = objectMapper.readValue(response, new TypeReference<List<Role>>(){});
        Assert.assertEquals(roleList.size(), rolesInDB.size());
    }

    @Test
    @WithMockUser(roles = {"ADMINISTRATOR"})
    void delete() throws Exception {
        Role roleInDB = rolesInDB.get(RANDOM.nextInt(rolesInDB.size()));

        mockMvc.perform(MockMvcRequestBuilders.post("/users/roles/delete")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(roleInDB))
        )
                .andDo(print())
                .andExpect(status().isOk());

//        Role nonexistent
        mockMvc.perform(MockMvcRequestBuilders.post("/users/roles/delete")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(roleInDB))
        )
                .andDo(print())
                .andExpect(status().isNotFound());

        //        Bad request
        Role role = new Role();
        mockMvc.perform(MockMvcRequestBuilders.post("/users/roles/delete")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(role))
        )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = {"ADMINISTRATOR"})
    void update() throws Exception {
        Role roleInDB = rolesInDB.get(RANDOM.nextInt(rolesInDB.size()));
        roleInDB.setRole("updated_name");

        String response = mockMvc.perform(MockMvcRequestBuilders.post("/users/roles/update")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(roleInDB))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Role responseRole = objectMapper.readValue(response, Role.class);
        Assert.assertEquals(responseRole.getRole(), "updated_name");

//        Bad Request
        Role roleWithEmptyRequiredFields = new Role();
        roleWithEmptyRequiredFields.setId(roleInDB.getId());
        mockMvc.perform(MockMvcRequestBuilders.post("/users/roles/update")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(roleWithEmptyRequiredFields))
        )
                .andDo(print())
                .andExpect(status().isBadRequest());

//        Nonexistent id
        roleInDB.setId(999L);
        mockMvc.perform(MockMvcRequestBuilders.post("/users/roles/update")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(roleInDB))
        )
                .andDo(print())
                .andExpect(status().isNotFound());

        //        No id set
        Role role = new Role();
        mockMvc.perform(MockMvcRequestBuilders.post("/users/roles/update")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(role))
        )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
