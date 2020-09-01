package com.javariga6.yugioh.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javariga6.yugioh.model.*;
import com.javariga6.yugioh.repository.ArticleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class ArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ArticleRepository articleRepository;

    public ObjectMapper objectMapper;
    private List<Article> articlesInDB;

    private final String testString = "String for testing";

    private static final Random RANDOM = new Random();
    public static <T extends Enum<?>> T randomEnum(Class<T> clazz){
        int x = RANDOM.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }

    @BeforeEach
    public void before(){
        for (int i=0; i<5; ++i){
            Article article = new Article();
            article.setCardType(randomEnum(CardType.class));
            article.setRarity(randomEnum(Rarity.class));
            article.setEdition(randomEnum(Edition.class));
            article.setCardName(testString + i + "_name");
            article.setBoosterSet(testString + i + "_booster_set");
            articleRepository.save(article);
            this.articlesInDB = articleRepository.findAll();
            this.objectMapper = new ObjectMapper();
        }

    }

    @Test
    @WithMockUser(roles = {"ADMINISTRATOR"})
    void findById() throws Exception {
        Article articleInDB = articlesInDB.get(1);
        mockMvc.perform(get ("/article/id/"+articleInDB.getId())
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(articleInDB)))
                .andReturn();
    }

    @Test
    void save() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}
