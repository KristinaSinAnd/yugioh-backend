package com.javariga6.yugioh.service;

import com.javariga6.yugioh.model.Articles;
import com.javariga6.yugioh.repository.ArticlesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.util.List;


public class ArticlesService {

    @Autowired
    private ArticlesRepository articlesRepository;

    public List<Articles> getAllArticles(){
        return articlesRepository.findAll();
    }

    public Articles getArticlesById(Long id){
        return articlesRepository.getOne(id);
    }

    public List<Articles> getAllByCardName(String cardName){
        return articlesRepository.findByCardName(cardName);
    }


}
