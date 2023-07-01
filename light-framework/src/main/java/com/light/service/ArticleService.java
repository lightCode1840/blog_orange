package com.light.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.light.domin.ResponseResult;
import com.light.domin.entity.Article;

/**
 * @author lilepingstart
 * @creat 2023-06-17 20:52
 */
public interface ArticleService extends IService<Article> {
    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Long id);
}
