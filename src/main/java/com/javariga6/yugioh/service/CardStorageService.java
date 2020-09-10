package com.javariga6.yugioh.service;

import com.javariga6.yugioh.exceptions.BadDataInRequestException;
import com.javariga6.yugioh.exceptions.IdInUseException;
import com.javariga6.yugioh.exceptions.ResourceNotFoundException;
import com.javariga6.yugioh.model.CardStorage;
import com.javariga6.yugioh.model.Role;
import com.javariga6.yugioh.repository.CardStorageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardStorageService {
    final CardStorageRepository cardStorageRepository;

    public CardStorageService(CardStorageRepository cardStorageRepository) {
        this.cardStorageRepository = cardStorageRepository;
    }

    public CardStorage saveCardStorage(CardStorage cardStorage) {
        if(cardStorage.getId()!=null) {
            if (cardStorageRepository.existsById(cardStorage.getId())) {
                throw new IdInUseException();
            }
        }
        return cardStorageRepository.save(cardStorage);
    }

//    public void findCardStorageById(Long id) {
//        cardStorageRepository.findById(id);
//    }

    public CardStorage updateCardStorage(CardStorage cardStorage) {
       if(cardStorage.getId()==null){
           throw new BadDataInRequestException();
       }
        if(!cardStorageRepository.existsById(cardStorage.getId())){
            throw new ResourceNotFoundException();
        }
        return cardStorageRepository.save(cardStorage);
    }

    public void delete(CardStorage cardStorage) {
        if (cardStorage.getId() == null){
            throw new BadDataInRequestException();
        }
        CardStorage cardStorageFromRepo = cardStorageRepository.findById(cardStorage.getId())
                .orElseThrow(ResourceNotFoundException::new);
        cardStorageRepository.delete(cardStorageFromRepo);
    }

//    public void deleteCardStorageById(Long id) {
//        cardStorageRepository.deleteById(id);
//    }

    public List<CardStorage> getAll() {
        return cardStorageRepository.findAll();
    }


//    todo for csv import
    public CardStorage findByName(String name){
        return cardStorageRepository.findFirstByStorageName(name);
    }

}
