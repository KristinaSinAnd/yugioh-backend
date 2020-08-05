package com.javariga6.yugioh.controller;

import com.javariga6.yugioh.model.Article;
import com.javariga6.yugioh.model.CardType;
import com.javariga6.yugioh.model.Edition;
import com.javariga6.yugioh.model.Rarity;
import com.javariga6.yugioh.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rest/Article.svc")
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

    @PostMapping
    public void save(@RequestBody Article article) {
        articleService.saveArticle(article);
    }

    @GetMapping("/delete/id/{id}")
    public void deleteArticleById(@PathVariable Long id) {
        articleService.deleteById(id);
    }

    @GetMapping("get/all")
    public void findAllArticles(@RequestBody Article article){articleService.findAllArticles();}

    @PutMapping
    public void update(@RequestBody Article article){articleService.updateArticle(article);}

}
