package com.javariga6.yugioh.controller;

import com.javariga6.yugioh.model.Articles;
import com.javariga6.yugioh.service.ArticlesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rest/Articles.svc")
public class ArticlesController {

        @Autowired
        private ArticlesService articlesService;

        @GetMapping("/articles/{id}")
        public Articles findById(@PathVariable Long id)
        {
            return articlesService.getArticlesById(id);
        }

}
