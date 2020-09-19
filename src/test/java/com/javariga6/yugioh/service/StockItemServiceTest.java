package com.javariga6.yugioh.service;

import com.javariga6.yugioh.exceptions.BadDataInRequestException;
import com.javariga6.yugioh.exceptions.IdInUseException;
import com.javariga6.yugioh.exceptions.ResourceNotFoundException;
import com.javariga6.yugioh.model.Article;
import com.javariga6.yugioh.model.CardStorage;
import com.javariga6.yugioh.model.Role;
import com.javariga6.yugioh.model.StockItem;
import com.javariga6.yugioh.repository.ArticleRepository;
import com.javariga6.yugioh.repository.CardStorageRepository;
import com.javariga6.yugioh.repository.StockItemRepository;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.regex.Matcher;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StockItemServiceTest {

    @Autowired
    @InjectMocks
    private StockItemService stockItemService;

    @Mock
    private StockItemRepository stockItemRepository;

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private CardStorageRepository cardStorageRepository;

    StockItem stockItemWithId = new StockItem();
    StockItem stockItemNotExisting = new StockItem();
    StockItem stockItemWithoutId = new StockItem();

    CardStorage cardStorage = new CardStorage();
    Article article = new Article();

    @BeforeEach
    void setUp() {
        cardStorage.setId(1L);
        cardStorage.setStorageName("test_storage");

        article.setId(1L);
        article.setBoosterSet("test_booster_set_1");
        article.setCardName("test_card_name_1");

        this.stockItemWithId.setId(1L);
        this.stockItemWithId.setCardStorage(cardStorage);
        this.stockItemWithId.setArticle(article);
        this.stockItemWithId.setCardValue(BigDecimal.valueOf(100.11));

        this.stockItemNotExisting.setId(99L);
        this.stockItemNotExisting.setCardStorage(cardStorage);
        this.stockItemNotExisting.setArticle(article);
        this.stockItemNotExisting.setCardValue(BigDecimal.valueOf(100.11));

        this.stockItemWithoutId.setCardStorage(cardStorage);
        this.stockItemWithoutId.setArticle(article);
        this.stockItemWithoutId.setCardValue(BigDecimal.valueOf(100.11));
    }

    @Test
    void saveStockItem() {
        Mockito.when(stockItemRepository.save(stockItemWithoutId))
                .thenReturn(stockItemWithoutId);
        Mockito.when(stockItemRepository.existsById(1L))
                .thenReturn(true);
        Mockito.when(articleRepository.findById(1L))
                .thenReturn(Optional.of(article));
        Mockito.when(cardStorageRepository.findById(1L))
                .thenReturn(Optional.of(cardStorage));

        Assert.assertEquals(stockItemService.saveStockItem(stockItemWithoutId), stockItemWithoutId);
        Assert.assertThrows(IdInUseException.class, ()->stockItemService.saveStockItem(stockItemWithId));
    }

    @Test
    void delete() {
        Mockito.when(stockItemRepository.findById(99L))
                .thenReturn(Optional.empty());

        Assert.assertThrows(ResourceNotFoundException.class, ()->stockItemService.delete(stockItemNotExisting));
        Assert.assertThrows(BadDataInRequestException.class, ()->stockItemService.delete(stockItemWithoutId));
    }

    @Test
    void updateStockItem() {
        Mockito.when(stockItemRepository.findById(1L))
                .thenReturn(Optional.of(stockItemWithId));
        Mockito.when(stockItemRepository.findById(99L))
                .thenReturn(Optional.empty());
        Mockito.when(stockItemRepository.save(stockItemWithId))
                .thenReturn(stockItemWithId);
        Mockito.when(cardStorageRepository.findById(1L))
                .thenReturn(Optional.of(cardStorage));

        Assert.assertEquals(stockItemService.updateStockItem(stockItemWithId), stockItemWithId);
        Assert.assertThrows(ResourceNotFoundException.class, ()->stockItemService.updateStockItem(stockItemNotExisting));
        Assert.assertThrows(BadDataInRequestException.class, ()->stockItemService.updateStockItem(stockItemWithoutId));
    }
}
