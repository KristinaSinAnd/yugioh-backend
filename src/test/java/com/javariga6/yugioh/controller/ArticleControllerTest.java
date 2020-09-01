package com.javariga6.yugioh.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.javariga6.yugioh.model.*;
import com.javariga6.yugioh.repository.ArticleRepository;
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
    public ObjectWriter objectWriter;
    private List<Article> articlesInDB;

    private final String testString = "String for testing";

    private static final Random RANDOM = new Random();
    public static <T extends Enum<?>> T randomEnum(Class<T> clazz){
        int x = RANDOM.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }

    @BeforeEach
    public void before(){
        articleRepository.deleteAll();
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
            this.objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        }
    }

    @Test
    @WithMockUser(roles = {"ADMINISTRATOR"})
    void findById() throws Exception {
        Article articleInDB = articlesInDB.get(RANDOM.nextInt(articlesInDB.size()));

        mockMvc.perform(get ("/article/id/"+articleInDB.getId())
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(articleInDB)))
                .andReturn();

        mockMvc.perform(get ("/article/id/999")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();

        mockMvc.perform(get ("/article/id/abc")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

    }

    @Test
    @WithMockUser(roles = {"ADMINISTRATOR"})
    void save() throws Exception {
        Article newArticle = new Article();
        newArticle.setCardType(randomEnum(CardType.class));
        newArticle.setRarity(randomEnum(Rarity.class));
        newArticle.setEdition(randomEnum(Edition.class));
        newArticle.setCardName(testString + "_name_new");
        newArticle.setBoosterSet(testString + "_booster_set_new");

        String response = mockMvc.perform(MockMvcRequestBuilders.post("/article/save")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newArticle))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Article responseArticle = objectMapper.readValue(response, Article.class);
        newArticle.setId(responseArticle.getId());
        Assert.assertEquals(newArticle.toString(), responseArticle.toString());

//        Bad request
        Article emptyArticle = new Article();
        mockMvc.perform(MockMvcRequestBuilders.post("/article/save")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(emptyArticle))
        )
                .andDo(print())
                .andExpect(status().isBadRequest());

//        Article with existing id
        mockMvc.perform(MockMvcRequestBuilders.post("/article/save")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(responseArticle))
        )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = {"ADMINISTRATOR"})
    void update() throws Exception {
        Article articleInDB = articlesInDB.get(RANDOM.nextInt(articlesInDB.size()));
        articleInDB.setCardName("updated_name");

        String response = mockMvc.perform(MockMvcRequestBuilders.post("/article/update")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(articleInDB))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Article responseArticle = objectMapper.readValue(response, Article.class);
        Assert.assertEquals(responseArticle.getCardName(), "updated_name");

//        Bad Request
        Article articleWithEmptyRequiredFields = new Article();
        articleWithEmptyRequiredFields.setId(articleInDB.getId());
        articleWithEmptyRequiredFields.setCardType(randomEnum(CardType.class));
        articleWithEmptyRequiredFields.setRarity(randomEnum(Rarity.class));
        articleWithEmptyRequiredFields.setEdition(randomEnum(Edition.class));
        mockMvc.perform(MockMvcRequestBuilders.post("/article/update")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(articleWithEmptyRequiredFields))
        )
                .andDo(print())
                .andExpect(status().isBadRequest());

//        Nonexistent id
        articleInDB.setId(999L);
        mockMvc.perform(MockMvcRequestBuilders.post("/article/update")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(articleInDB))
        )
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    @Test
    @WithMockUser(roles = {"ADMINISTRATOR"})
    void delete() throws Exception {
        Article articleInDB = articlesInDB.get(RANDOM.nextInt(articlesInDB.size()));

        mockMvc.perform(MockMvcRequestBuilders.post("/article/delete")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(articleInDB))
        )
                .andDo(print())
                .andExpect(status().isOk());

//        Article nonexistent
        mockMvc.perform(MockMvcRequestBuilders.post("/article/delete")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(articleInDB))
        )
                .andDo(print())
                .andExpect(status().isNotFound());

        //        Bad request
        Article article = new Article();
        mockMvc.perform(MockMvcRequestBuilders.post("/article/delete")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(article))
        )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
