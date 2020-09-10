package com.javariga6.yugioh.service;

import com.javariga6.yugioh.exceptions.BadDataInRequestException;
import com.javariga6.yugioh.exceptions.IdInUseException;
import com.javariga6.yugioh.exceptions.ResourceNotFoundException;
import com.javariga6.yugioh.model.*;
import com.javariga6.yugioh.repository.ArticleRepository;
import com.javariga6.yugioh.repository.CardStorageRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CardStorageServiceTest {

    @Autowired
    @InjectMocks
    private CardStorageService cardStorageService;

    @Mock
    private CardStorageRepository cardStorageRepository;

    CardStorage cardStorageWithId = new CardStorage();
    CardStorage cardStorageNotExisting = new CardStorage();
    CardStorage cardStorageWithoutId = new CardStorage();

    @BeforeEach
    void setUp() {
        this.cardStorageWithId.setId(1L);
        this.cardStorageWithId.setStorageName("test_storage");

        this.cardStorageNotExisting.setId(99L);
        this.cardStorageNotExisting.setStorageName("test_storage_not_existing");

        this.cardStorageWithoutId.setStorageName("test_storage_no_id");
    }

    @Test
    void saveCardStorage() {
        Mockito.when(cardStorageRepository.save(cardStorageWithoutId))
                .thenReturn(cardStorageWithoutId);
        Mockito.when(cardStorageRepository.existsById(1L))
                .thenReturn(true);

        Assert.assertEquals(cardStorageService.saveCardStorage(cardStorageWithoutId), cardStorageWithoutId);
        Assert.assertThrows(IdInUseException.class, ()->cardStorageService.saveCardStorage(cardStorageWithId));

    }

    @Test
    void updateCardStorage() {
        Mockito.when(cardStorageRepository.existsById(1L))
                .thenReturn(true);
        Mockito.when(cardStorageRepository.existsById(99L))
                .thenReturn(false);
        Mockito.when(cardStorageRepository.save(cardStorageWithId))
                .thenReturn(cardStorageWithId);

        Assert.assertEquals(cardStorageService.updateCardStorage(cardStorageWithId), cardStorageWithId);
        Assert.assertThrows(ResourceNotFoundException.class, ()->cardStorageService.updateCardStorage(cardStorageNotExisting));
    }

    @Test
    void delete() {
        Mockito.when(cardStorageRepository.findById(99L))
                .thenReturn(Optional.empty());

        Assert.assertThrows(ResourceNotFoundException.class, ()->cardStorageService.delete(cardStorageNotExisting));
        Assert.assertThrows(BadDataInRequestException.class, ()->cardStorageService.delete(cardStorageWithoutId));
    }
}