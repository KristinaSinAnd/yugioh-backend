package com.javariga6.yugioh.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javariga6.yugioh.model.Article;
import com.javariga6.yugioh.model.CardCondition;
import com.javariga6.yugioh.model.CardStorage;
import com.javariga6.yugioh.model.StockItem;
import com.javariga6.yugioh.repository.ArticleRepository;
import com.javariga6.yugioh.repository.CardStorageRepository;
import com.javariga6.yugioh.repository.StockItemRepository;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class StockItemControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StockItemRepository stockItemRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CardStorageRepository cardStorageRepository;

    private static String testStringName = "Test name";
    public   Article testArticle;
    public CardStorage testCardStorage;
    public ObjectMapper objectMapper;


    @BeforeEach
    public void before(){
        stockItemRepository.deleteAll();
        testArticle = articleRepository.save(new Article(testStringName, testStringName));
        testCardStorage = cardStorageRepository.save(new CardStorage(testStringName));
        objectMapper = new ObjectMapper();
    }

    @Test
    @WithMockUser(roles = {"ADMINISTRATOR"})
    void update() throws Exception {
        StockItem stockItem = new StockItem();
        stockItem.setId(1L);
        stockItem.setComments(testStringName);
        stockItemRepository.save(stockItem);

        JSONObject itemWithExistingId = new JSONObject()
                .put("id", 1)
                .put("comments", testStringName);

        JSONObject itemWithNoneExistingId = new JSONObject()
                .put("id", 2);

        mockMvc.perform(post("/stockitem/update")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(itemWithExistingId.toString())
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(itemWithExistingId.toString()))
                .andReturn().getResponse().getContentAsString();

        mockMvc.perform(post("/stockitem/update")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(itemWithNoneExistingId.toString())
        )
                .andDo(print())
                .andExpect(status().is(404))
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    @WithMockUser(roles = {"ADMINISTRATOR"})
    void save() throws Exception{

        StockItem stockItem = new StockItem();
        stockItem.setArticle(testArticle);
        stockItem.setCardStorage(testCardStorage);

        mockMvc.perform(post("/stockitem/create")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(stockItem))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        CardStorage cardStorageNotInDb = new CardStorage();
        cardStorageNotInDb.setId(999L);
        stockItem.setCardStorage(cardStorageNotInDb);

//        Bad request. cardStorage in stockItem objects in not in database.
        mockMvc.perform(post("/stockitem/create")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(stockItem))
        )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{'error':'error-0002'}"))
                .andReturn().getResponse().getContentAsString();
    }


}
