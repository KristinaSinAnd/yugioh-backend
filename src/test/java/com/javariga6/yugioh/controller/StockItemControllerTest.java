package com.javariga6.yugioh.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javariga6.yugioh.model.Article;
import com.javariga6.yugioh.model.CardStorage;
import com.javariga6.yugioh.model.StockItem;
import com.javariga6.yugioh.repository.ArticleRepository;
import com.javariga6.yugioh.repository.CardStorageRepository;
import com.javariga6.yugioh.repository.StockItemRepository;
import org.json.JSONObject;
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

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
        stockItem.setArticle(testArticle);
        stockItem.setCardStorage(testCardStorage);
        stockItem.setComments(testStringName);
        stockItem = stockItemRepository.save(stockItem);
        stockItem.setComments("new comment");


        StockItem stockItemNotInDataBase = new StockItem();
        stockItemNotInDataBase.setArticle(testArticle);
        stockItemNotInDataBase.setCardStorage(testCardStorage);
        stockItemNotInDataBase.setComments(testStringName);
        stockItemNotInDataBase.setId(999L);

        mockMvc.perform(post("/stockitem/update")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(stockItem))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(stockItem)))
                .andReturn().getResponse().getContentAsString();

        mockMvc.perform(post("/stockitem/update")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(stockItemNotInDataBase))
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
                .andReturn();
    }


    @Test
    @WithMockUser(roles = {"ADMINISTRATOR"})
    void delete() throws Exception {
        StockItem stockItem1 = new StockItem();
        stockItem1.setArticle(testArticle);
        stockItem1.setCardStorage(testCardStorage);
        StockItem stockItem2 = new StockItem();
        stockItem2.setArticle(testArticle);
        stockItem2.setCardStorage(testCardStorage);
        StockItem stockItem3 = new StockItem();
        stockItem3.setArticle(testArticle);
        stockItem3.setCardStorage(testCardStorage);
        stockItemRepository.save(stockItem1);
        stockItemRepository.save(stockItem2);
        stockItemRepository.save(stockItem3);

        Assert.assertEquals(3, stockItemRepository.findAll().size());

        StockItem stockItemToDelete = new StockItem();
        stockItemToDelete.setArticle(testArticle);
        stockItemToDelete.setCardStorage(testCardStorage);
        stockItemToDelete.setComments("item 1");
        stockItemToDelete.setId(1L);

        mockMvc.perform(post("/stockitem/delete")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(stockItem1))
        )
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        Assert.assertEquals(stockItemRepository.findAll().size(), 2);

//        Bad request, empty object
        mockMvc.perform(post("/stockitem/delete")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new StockItem()))
        )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

        mockMvc.perform(post("/stockitem/delete")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(stockItemToDelete))
        )
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    @WithMockUser(roles = {"ADMINISTRATOR"})
    void findStockItemById() throws Exception {
        StockItem stockItem1 = new StockItem();
        stockItem1.setArticle(testArticle);
        stockItem1.setCardStorage(testCardStorage);
        StockItem stockItem2 = new StockItem();
        stockItem2.setArticle(testArticle);
        stockItem2.setCardStorage(testCardStorage);
        StockItem stockItem3 = new StockItem();
        stockItem3.setArticle(testArticle);
        stockItem3.setCardStorage(testCardStorage);
        stockItemRepository.save(stockItem1);
        stockItemRepository.save(stockItem2);
        stockItemRepository.save(stockItem3);

        mockMvc.perform(get ("/stockitem/get/id/"+stockItem1.getId())
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

//        Bad request, no such id in db
        mockMvc.perform(get ("/stockitem/get/id/999")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();

        mockMvc.perform(get ("/stockitem/get/id/abc")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

    }
}


