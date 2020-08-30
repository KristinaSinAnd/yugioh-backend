package com.javariga6.yugioh.controller;

import com.javariga6.yugioh.model.UserTO;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void register() throws Exception {
        JSONObject json = new JSONObject()
                .put("email", "epasts@mail.com")
                .put("surname", "surname")
                .put("password", "parole")
                .put("name", "user");
        JSONObject login = new JSONObject()
                .put("email", "admin@admin.com")
                .put("password", "admin");

        String jsonToken = mockMvc.perform(post("/login")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(login.toString())
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        String token = new JSONObject(jsonToken).getString("token");



        mockMvc.perform(post("/user/delete")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .content(json.toString())
        )
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(post("/user/register")
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
        .content(json.toString())
        )
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(post("/user/register")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(json.toString())
        )
                .andDo(print())
                .andExpect(status().isBadRequest());

    }
}
