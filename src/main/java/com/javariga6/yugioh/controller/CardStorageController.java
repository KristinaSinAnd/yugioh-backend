package com.javariga6.yugioh.controller;


import com.javariga6.yugioh.model.CardStorage;
import com.javariga6.yugioh.model.User;
import com.javariga6.yugioh.service.CardStorageService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cardstorage")
public class CardStorageController {

    private final CardStorageService cardStorageService;

    public CardStorageController(CardStorageService cardStorageService) {
        this.cardStorageService = cardStorageService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public CardStorage save(@RequestBody @Valid CardStorage cardStorage) {
        return cardStorageService.saveCardStorage(cardStorage);
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public CardStorage update(@RequestBody @Valid CardStorage cardStorage) {
        return cardStorageService.updateCardStorage(cardStorage);
    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public void delete(@RequestBody @Valid CardStorage cardStorage) {
        cardStorageService.delete(cardStorage);
    }

    @GetMapping("/all")
    public List<CardStorage> getAllCardStorages() {
        return cardStorageService.getAll();
    }

}
