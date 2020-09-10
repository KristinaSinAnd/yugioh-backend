package com.javariga6.yugioh.service;

import com.javariga6.yugioh.exceptions.BadDataInRequestException;
import com.javariga6.yugioh.exceptions.IdInUseException;
import com.javariga6.yugioh.exceptions.ResourceNotFoundException;
import com.javariga6.yugioh.model.Article;
import com.javariga6.yugioh.model.CardType;
import com.javariga6.yugioh.model.Edition;
import com.javariga6.yugioh.model.Rarity;
import com.javariga6.yugioh.repository.ArticleRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @Autowired
    @InjectMocks
    private ArticleService articleService;

    @Mock
    private ArticleRepository articleRepository;

    Article articleWithId = new Article();
    Article articleNotExisting = new Article();
    Article articleWithoutId = new Article();

    @BeforeEach
    void setUp() {
        this.articleWithId.setId(1L);
        this.articleWithId.setBoosterSet("test_booster_set_1");
        this.articleWithId.setCardName("test_card_name_1");
        this.articleWithId.setCardType(CardType.MONSTER);
        this.articleWithId.setRarity(Rarity.COLLECTORS_RARE);
        this.articleWithId.setEdition(Edition.UNLIMITED);

        this.articleNotExisting.setId(99L);
        this.articleNotExisting.setBoosterSet("test_booster_set_2");
        this.articleNotExisting.setCardName("test_card_name_2");
        this.articleNotExisting.setCardType(CardType.MONSTER);
        this.articleNotExisting.setRarity(Rarity.COLLECTORS_RARE);
        this.articleNotExisting.setEdition(Edition.UNLIMITED);

        this.articleWithoutId.setBoosterSet("test_booster_set_4");
        this.articleWithoutId.setCardName("test_card_name_4");
        this.articleWithoutId.setCardType(CardType.MONSTER);
        this.articleWithoutId.setRarity(Rarity.COLLECTORS_RARE);
        this.articleWithoutId.setEdition(Edition.UNLIMITED);
    }

    @Test
    void getArticleById() throws Exception{
        Mockito.when(articleRepository.findById(1L))
                .thenReturn(Optional.of(articleWithId));

        Assert.assertEquals(articleService.getArticleById(1L).getId(), articleWithId.getId());
        Assert.assertEquals(articleService.getArticleById(1L).getCardName(), articleWithId.getCardName());
        Assert.assertEquals(articleService.getArticleById(1L).getBoosterSet(), articleWithId.getBoosterSet());

        Assert.assertThrows(ResourceNotFoundException.class, ()->articleService.getArticleById(999L));
        Assert.assertThrows(BadDataInRequestException.class, ()->articleService.getArticleById(null));
    }

    @Test
    void saveArticle() {
        Mockito.when(articleRepository.save(articleWithoutId))
                .thenReturn(articleWithoutId);
        Mockito.when(articleRepository.existsById(1L))
                .thenReturn(true);

        Assert.assertEquals(articleService.saveArticle(articleWithoutId), articleWithoutId);
        Assert.assertThrows(IdInUseException.class, ()->articleService.saveArticle(articleWithId));
    }

    @Test
    void updateArticle() {
        Mockito.when(articleRepository.findById(1L))
                .thenReturn(Optional.of(articleWithId));
        Mockito.when(articleRepository.findById(99L))
                .thenReturn(Optional.empty());
        Mockito.when(articleRepository.save(articleWithId))
                .thenReturn(articleWithId);

        Assert.assertEquals(articleService.updateArticle(articleWithId), articleWithId);
        Assert.assertThrows(ResourceNotFoundException.class, ()->articleService.updateArticle(articleNotExisting));
    }

    @Test
    void deleteArticle() {
        Mockito.when(articleRepository.findById(99L))
                .thenReturn(Optional.empty());


        Assert.assertThrows(ResourceNotFoundException.class, ()->articleService.deleteArticle(articleNotExisting));
        Assert.assertThrows(BadDataInRequestException.class, ()->articleService.deleteArticle(articleWithoutId));
    }
}