package com.javariga6.yugioh.controller;

import com.javariga6.yugioh.model.CardCondition;
import com.javariga6.yugioh.model.CardType;
import com.javariga6.yugioh.model.Edition;
import com.javariga6.yugioh.model.Rarity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/enum")
public class EnumController {
    @GetMapping("card_type")
    public List<CardType> getCardTypes(){
        return Arrays.asList(CardType.values());
    }
     @GetMapping("card_condition")
    public List<CardCondition> getCardConditions(){
        return Arrays.asList(CardCondition.values());
    }
     @GetMapping("edition")
    public List<Edition> getEditions(){
        return Arrays.asList(Edition.values());
    }
     @GetMapping("rarity")
    public List<Rarity> getRaritys(){
        return Arrays.asList(Rarity.values());
    }
}
