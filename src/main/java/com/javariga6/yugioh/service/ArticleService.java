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
        return articleRepository.findById(id).orElse(null);
    }

    public List<Article> getAllByCardName(String cardName){
        return articleRepository.findByCardName(cardName);
    }

    public void saveArticle(Article article){
        articleRepository.save(article);
    }

    public void deleteById(Long id) {
        articleRepository.deleteById(id);
    }

    public List<Article> getArticleByBoosterSet(String boosterSet) {
        return articleRepository.findByBoosterSet(boosterSet); }

    public List<Article> findAllArticles(){
        return articleRepository.findAll();
    }
    public void updateArticle(Article article) {
        articleRepository.save(article);
    }

    public void deleteArticle(Article article) {articleRepository.delete(article);
    }
    public List<Article> findByArticle(Article article) {
        return articleRepository.findAllByBoosterSetAndCardNameAndCardTypeAndEditionAndRarity(
                article.getBoosterSet(),
                article.getCardName(),
                article.getCardType(),
                article.getEdition(),
                article.getRarity()
        );
    }
}
