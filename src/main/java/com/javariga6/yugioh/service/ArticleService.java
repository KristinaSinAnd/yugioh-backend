package com.javariga6.yugioh.service;

import com.javariga6.yugioh.model.Article;
import com.javariga6.yugioh.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    public List<Article> getAllArticles(){
        return articleRepository.findAll();
    }

    public Article getArticleById(Long id){
        return articleRepository.getOne(id);
    }

    public List<Article> getAllByCardName(String cardName){
        return articleRepository.findByCardName(cardName);
    }


}
