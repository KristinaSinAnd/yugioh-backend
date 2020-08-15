package com.javariga6.yugioh.controller;

import com.javariga6.yugioh.model.Article;
import com.javariga6.yugioh.model.CardType;
import com.javariga6.yugioh.model.Edition;
import com.javariga6.yugioh.model.Rarity;
import com.javariga6.yugioh.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/id/{id}")
    public Article findById(@PathVariable Long id) {
        System.out.println(articleService.getArticleById(id));
        return articleService.getArticleById(id);
    }

    @GetMapping("/booster/{booster_set}")
    public Article findByBoosterSet(@PathVariable String boosterSet){return (Article) articleService.getArticleByBoosterSet(boosterSet);}

    @PostMapping ("/save")
    public void save(@RequestBody Article article) {
        System.out.println(article);
        articleService.saveArticle(article);
    }

    @GetMapping("/delete/id/{id}")
    public void deleteArticleById(@PathVariable Long id) {
        articleService.deleteById(id);
    }

    @GetMapping("/all")
    public List<Article> findAllArticles(){
        return articleService.findAllArticles();
    }

    @PostMapping ("/update")
    public void update(@RequestBody Article article){articleService.updateArticle(article);}

    @PostMapping ("/delete")
    public void delete (@RequestBody Article article) {articleService.deleteArticle(article);}


}
