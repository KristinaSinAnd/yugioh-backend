package com.javariga6.yugioh.controller;

import com.javariga6.yugioh.model.Article;
import com.javariga6.yugioh.model.CardCondition;
import com.javariga6.yugioh.model.CardStorage;
import com.javariga6.yugioh.model.StockItem;
import com.javariga6.yugioh.repository.ArticleRepository;
import com.javariga6.yugioh.repository.CardStorageRepository;
import com.javariga6.yugioh.repository.StockItemRepository;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

    private static final String testStringName = "Test name";
    public   Article testArticle;
    public CardStorage testCardStorage;

    @Before
    public void before(){
        stockItemRepository.deleteAll();
        testArticle = articleRepository.save(new Article(testStringName));
        testCardStorage = cardStorageRepository.save(new CardStorage(testStringName));
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

        JSONObject itemToSave = new JSONObject()
                .put("cardCondition", CardCondition.EXCELLENT)
                .put("cardValue", 1.5)
                .put("inShop", true)
                .put("comments", "Test comment")
                .put("cardStorage", testCardStorage)
                .put("article", testArticle);

        mockMvc.perform(post("/stockitem/create")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(itemToSave.toString())
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(itemToSave.toString()))
                .andReturn().getResponse().getContentAsString();
    }

}
