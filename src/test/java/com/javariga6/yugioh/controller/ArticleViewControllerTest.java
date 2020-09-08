package com.javariga6.yugioh.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javariga6.yugioh.model.*;
import com.javariga6.yugioh.repository.ArticleRepository;
import com.javariga6.yugioh.repository.ArticleViewRepository;
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
class ArticleViewControllerTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;
    private static final Random RANDOM = new Random();
    private final String testString = "String for testing";
    private List<Article> articlesInDB;

    @BeforeEach
    public void before() {
        articleRepository.deleteAll();
        for (int i = 0; i < 5; ++i) {
            Article article = new Article();
            article.setCardType(randomEnum(CardType.class));
            article.setRarity(randomEnum(Rarity.class));
            article.setEdition(randomEnum(Edition.class));
            article.setCardName(testString + i + "_name");
            article.setBoosterSet(testString + i + "_booster_set");
            articleRepository.save(article);
        }
        this.objectMapper = new ObjectMapper();
        this.articlesInDB = articleRepository.findAll();
    }

    @Test
    @WithMockUser(roles = {"ADMINISTRATOR"})
    void getAllArticlesView() throws Exception {
        objectMapper = new ObjectMapper();
            String response = mockMvc.perform(MockMvcRequestBuilders.get("/articleview/all")
                    .contentType(APPLICATION_JSON)
                    .accept(APPLICATION_JSON)
            )
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();
            List<ArticleView> storageList = objectMapper.readValue(response, new TypeReference<List<ArticleView>>(){});

            Assert.assertEquals(storageList.size(), articlesInDB.size());
    }

    public static <T extends Enum<?>> T randomEnum(Class<T> clazz) {
        int x = RANDOM.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }
}
