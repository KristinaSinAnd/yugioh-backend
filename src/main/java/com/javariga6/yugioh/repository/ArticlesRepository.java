package com.javariga6.yugioh.repository;

import com.javariga6.yugioh.model.Articles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticlesRepository extends
        JpaRepository <Articles,Long> {

    List<Articles> findByCardName(String cardName);
}
