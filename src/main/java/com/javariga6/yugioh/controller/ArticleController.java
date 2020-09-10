package com.javariga6.yugioh.controller;

import com.javariga6.yugioh.model.Article;
import com.javariga6.yugioh.model.CardType;
import com.javariga6.yugioh.model.Edition;
import com.javariga6.yugioh.model.Rarity;
import com.javariga6.yugioh.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/id/{id}")
    public Article findById(@PathVariable Long id) {
        return articleService.getArticleById(id);
    }

    @GetMapping("/booster/{booster_set}")
    public Article findByBoosterSet(@PathVariable String boosterSet) {
        return (Article) articleService.getArticleByBoosterSet(boosterSet);
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public Article save(@RequestBody @Valid Article article) {
        return articleService.saveArticle(article);
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public Article update(@RequestBody @Valid Article article) {
        return articleService.updateArticle(article);
    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public void delete(@RequestBody @Valid Article article) {
        articleService.deleteArticle(article);
    }


}
