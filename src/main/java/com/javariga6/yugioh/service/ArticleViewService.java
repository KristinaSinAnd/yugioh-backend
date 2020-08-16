package com.javariga6.yugioh.service;

import com.javariga6.yugioh.model.ArticleView;
import com.javariga6.yugioh.repository.ArticleViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleViewService {

    @Autowired
    private ArticleViewRepository articleViewRepository;

    public List<ArticleView> getAllArticlesView(){return articleViewRepository.findAll();}


}
