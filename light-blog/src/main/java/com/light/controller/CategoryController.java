package com.light.controller;

import com.light.domin.ResponseResult;
import com.light.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lilepingstart
 * @creat 2023-06-18 17:18
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    //文章和分类是一对一的，多表查询，连表查询
    //禁止三张表以上查询，单表查询较好
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/getCategoryList")
    public ResponseResult getCategoryList(){
        return categoryService.getCategoryList();

    }


}
