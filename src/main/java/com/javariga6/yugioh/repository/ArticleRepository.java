package com.javariga6.yugioh.repository;

import com.javariga6.yugioh.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends
        JpaRepository <Article,Long> {

    List<Article> findByCardName(String cardName);

   List<Article> findByBoosterSet(String boosterSet);
}
