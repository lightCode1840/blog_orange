package com.light.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.light.domin.entity.Article;
import com.light.mapper.ArticleMapper;
import com.light.service.ArticleService;
import org.springframework.stereotype.Service;

/**
 * @author lilepingstart
 * @creat 2023-06-17 20:53
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

}
