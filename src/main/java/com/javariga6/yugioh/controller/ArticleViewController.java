package com.javariga6.yugioh.controller;

import com.javariga6.yugioh.model.Article;
import com.javariga6.yugioh.model.ArticleView;
import com.javariga6.yugioh.service.ArticleViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/articleview")
public class ArticleViewController {

    @Autowired
    private ArticleViewService articleViewService;

    @GetMapping("/all")
    public List<ArticleView> getAllArticlesView() {
        return articleViewService.getAllArticlesView();
    }


}


