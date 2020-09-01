package com.javariga6.yugioh.service;

import com.javariga6.yugioh.exceptions.IdInUseException;
import com.javariga6.yugioh.exceptions.ReferencedResourceNotFoundException;
import com.javariga6.yugioh.exceptions.ResourceNotFoundException;
import com.javariga6.yugioh.model.Article;
import com.javariga6.yugioh.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    public Article getArticleById(Long id) {
        return articleRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    public List<Article> getAllByCardName(String cardName) {
        return articleRepository.findByCardName(cardName);
    }

    public Article saveArticle(Article article) {
        if (article.getId() != null) {
            if (articleRepository.existsById(article.getId())) {
                throw new IdInUseException();
            }
        }
        return articleRepository.save(article);
    }

    public void deleteById(Long id) {
        articleRepository.deleteById(id);
    }

    public List<Article> getArticleByBoosterSet(String boosterSet) {
        return articleRepository.findByBoosterSet(boosterSet);
    }

    public List<Article> findAllArticles() {
        return articleRepository.findAll();
    }

    public Article updateArticle(Article article) {
        Article articleToUpdate = articleRepository.findById(article.getId())
                .orElseThrow(ResourceNotFoundException::new);
        return articleRepository.save(article);
    }

    public void deleteArticle(Article article) {
        articleRepository.delete(article);
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
