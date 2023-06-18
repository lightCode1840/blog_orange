package com.light.controller;

import com.light.domin.ResponseResult;
import com.light.domin.entity.Article;
import com.light.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

//    @RequestMapping("/list")
//    public List<Article> test(){
//        return articleService.list();
//    }
    @GetMapping("/hotArticleList")
    public ResponseResult hotArticle(){
        //查询热门文章，封装成responseResult返回
        ResponseResult responseResult=articleService.hotArticleList();
        return responseResult;
    }

    @GetMapping("/articleList")
    public ResponseResult articleList(Integer pageNum,Integer pageSize,Long categoryId){
        return articleService.articleList(pageNum,pageSize,categoryId);
    }
}
