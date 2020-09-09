package com.javariga6.yugioh.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javariga6.yugioh.model.CardStorage;
import com.javariga6.yugioh.repository.CardStorageRepository;
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
class CardStorageControllerTest {

    private static final Random RANDOM = new Random();
    private final String testString = "String for testing";
    public ObjectMapper objectMapper;

    @Autowired
    private CardStorageRepository cardStorageRepository;

    @Autowired
    private MockMvc mockMvc;

    private List<CardStorage> storagesInDB;

    @BeforeEach
    public void before() {
        cardStorageRepository.deleteAll();
        for (int i = 0; i < 5; ++i) {
            CardStorage storage = new CardStorage();
            storage.setStorageName("storage_" + i);
            cardStorageRepository.save(storage);
        }
        this.storagesInDB = cardStorageRepository.findAll();
        this.objectMapper = new ObjectMapper();
    }

    @Test
    @WithMockUser(roles = {"ADMINISTRATOR"})
    void save() throws Exception{
            CardStorage newCardStorage = new CardStorage();
            newCardStorage.setStorageName("storage_new");

            String response = mockMvc.perform(MockMvcRequestBuilders.post("/cardstorage/create")
                    .contentType(APPLICATION_JSON)
                    .accept(APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(newCardStorage))
            )
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();
            CardStorage responseCardStorage = objectMapper.readValue(response, CardStorage.class);
            newCardStorage.setId(responseCardStorage.getId());
            Assert.assertEquals(newCardStorage.toString(), responseCardStorage.toString());

            //        Bad request
            CardStorage emptyCardStorage = new CardStorage();
            mockMvc.perform(MockMvcRequestBuilders.post("/cardstorage/create")
                    .contentType(APPLICATION_JSON)
                    .accept(APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(emptyCardStorage))
            )
                    .andDo(print())
                    .andExpect(status().isBadRequest());

//        CardStorage with existing id
            mockMvc.perform(MockMvcRequestBuilders.post("/cardstorage/create")
                    .contentType(APPLICATION_JSON)
                    .accept(APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(responseCardStorage))
            )
                    .andDo(print())
                    .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = {"ADMINISTRATOR"})
    void update() throws Exception {
        CardStorage storageInDb = storagesInDB.get(RANDOM.nextInt(storagesInDB.size()));
        storageInDb.setStorageName("updated_name");

        String response = mockMvc.perform(MockMvcRequestBuilders.post("/cardstorage/update")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(storageInDb))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        CardStorage responseCardStorage = objectMapper.readValue(response, CardStorage.class);
        Assert.assertEquals(responseCardStorage.getStorageName(), "updated_name");

//        Bad Request
        CardStorage storageWithEmptyRequiredFields = new CardStorage();
        storageWithEmptyRequiredFields.setId(storageInDb.getId());
        mockMvc.perform(MockMvcRequestBuilders.post("/cardstorage/update")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(storageWithEmptyRequiredFields))
        )
                .andDo(print())
                .andExpect(status().isBadRequest());

//        Nonexistent id
        storageInDb.setId(999L);
        mockMvc.perform(MockMvcRequestBuilders.post("/cardstorage/update")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(storageInDb))
        )
                .andDo(print())
                .andExpect(status().isNotFound());

        //        No id set
        CardStorage storage = new CardStorage();
        mockMvc.perform(MockMvcRequestBuilders.post("/cardstorage/update")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(storage))
        )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = {"ADMINISTRATOR"})
    void delete() throws Exception {
        CardStorage storageInDb = storagesInDB.get(RANDOM.nextInt(storagesInDB.size()));

        mockMvc.perform(MockMvcRequestBuilders.post("/cardstorage/delete")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(storageInDb))
        )
                .andDo(print())
                .andExpect(status().isOk());

//        CardStorage nonexistent
        mockMvc.perform(MockMvcRequestBuilders.post("/cardstorage/delete")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(storageInDb))
        )
                .andDo(print())
                .andExpect(status().isNotFound());

        //        Bad request
        CardStorage storage = new CardStorage();
        mockMvc.perform(MockMvcRequestBuilders.post("/cardstorage/delete")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(storage))
        )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = {"ADMINISTRATOR"})
    void getAllCardStorages() throws Exception{
        String response = mockMvc.perform(MockMvcRequestBuilders.get("/cardstorage/all")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        List<CardStorage> storageList = objectMapper.readValue(response, new TypeReference<List<CardStorage>>(){});
        Assert.assertEquals(storageList.size(), storagesInDB.size());
    }
}
