package com.javariga6.yugioh.controller;

import com.javariga6.yugioh.model.Article;
import com.javariga6.yugioh.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rest/Article.svc")
public class ArticleController {

        @Autowired
        private ArticleService articleService;

        @GetMapping("/article/{id}")
        public Article findById(@PathVariable Long id)
        {
            return articleService.getArticleById(id);
        }

}