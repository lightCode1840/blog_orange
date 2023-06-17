package com.light.controller;

import com.light.domin.entity.Article;
import com.light.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lilepingstart
 * @creat 2023-06-17 20:57
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @RequestMapping("/list")
    public List<Article> test(){
        return articleService.list();
    }
}
