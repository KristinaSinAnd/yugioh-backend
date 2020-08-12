package com.javariga6.yugioh.controller;


import com.javariga6.yugioh.model.CardStorage;
import com.javariga6.yugioh.model.User;
import com.javariga6.yugioh.service.CardStorageService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cardstorage")
public class CardStorageController {

    private final CardStorageService cardStorageService;

    public CardStorageController(CardStorageService cardStorageService) {
        this.cardStorageService = cardStorageService;
    }

    @PostMapping ("/create")
    public void save(@RequestBody CardStorage cardStorage) {
        cardStorageService.saveCardStorage(cardStorage);
    }

    @GetMapping("/get/id/{id}")
    public void findCardStorageById(@PathVariable Long id) {
        cardStorageService.findCardStorageById(id);
    }

    @PutMapping
    public void update(@RequestBody CardStorage cardStorage) {
        cardStorageService.updateCardStorage(cardStorage);
    }

    @PostMapping("/delete")
    public void delete(@RequestBody CardStorage cardStorage) {
        cardStorageService.delete(cardStorage);
    }

    @GetMapping("/delete/id/{id}")
    public void deleteCardStorageById(@PathVariable Long id) {
        cardStorageService.deleteCardStorageById(id);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public List<CardStorage> getAllCardStorages(){
        return cardStorageService.getAll();
    }

    @GetMapping("/get/stockitems/{id}")
    public void findStockItemsByStorageId(@PathVariable Long id){cardStorageService.findCardStorageById(id);}
}
