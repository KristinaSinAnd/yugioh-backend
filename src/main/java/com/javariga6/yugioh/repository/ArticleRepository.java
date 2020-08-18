package com.javariga6.yugioh.repository;

import com.javariga6.yugioh.model.Article;
import com.javariga6.yugioh.model.CardType;
import com.javariga6.yugioh.model.Edition;
import com.javariga6.yugioh.model.Rarity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends
        JpaRepository <Article,Long> {

    List<Article> findByCardName(String cardName);

   List<Article> findByBoosterSet(String boosterSet);
   List<Article> findAllByBoosterSetAndCardNameAndCardTypeAndEditionAndRarity(
           String boosterSet,
           String cardName,
           CardType cardType,
           Edition edition,
           Rarity rarity
   );
}
