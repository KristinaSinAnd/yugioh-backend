package com.javariga6.yugioh.service;

import com.javariga6.yugioh.model.CardStorage;
import com.javariga6.yugioh.repository.CardStorageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardStorageService {
    final CardStorageRepository cardStorageRepository;

    public CardStorageService(CardStorageRepository cardStorageRepository) {
        this.cardStorageRepository = cardStorageRepository;
    }

    public void saveCardStorage(CardStorage cardStorage) {
        cardStorageRepository.save(cardStorage);
    }

    public void findCardStorageById(Long id) {
        cardStorageRepository.findById(id);
    }

    public void updateCardStorage(CardStorage cardStorage) {
        cardStorageRepository.save(cardStorage);
    }

    public void delete(CardStorage cardStorage) {
        cardStorageRepository.delete(cardStorage);
    }

    public void deleteCardStorageById(Long id) {
        cardStorageRepository.deleteById(id);
    }

    public List<CardStorage> getAll() {
        System.out.println(cardStorageRepository.findAll());
        return cardStorageRepository.findAll();
    }

}
