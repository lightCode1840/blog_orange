package com.light.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.light.constants.SystemConstants;
import com.light.domin.ResponseResult;
import com.light.domin.entity.Article;
import com.light.domin.entity.Category;
import com.light.mapper.CategoryMapper;
import com.light.service.ArticleService;
import com.light.service.CategoryService;
import com.light.utils.BeanCopyUtils;
import com.light.vo.CategoryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2023-06-18 17:15:14
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private ArticleService articleService;

    @Override
    public ResponseResult getCategoryList() {
        //查询已发表的文章
        LambdaQueryWrapper<Article> queryWrapper=new LambdaQueryWrapper();
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articles=articleService.list(queryWrapper);
        //将取出来的category_id去重
        Set<Long> categoryId = articles.stream()
                .map(article -> article.getCategoryId())
                .collect(Collectors.toSet());
        //然后查询category表，将结果封装返回,只有正常状态的才返回
        List<Category> categories = listByIds(categoryId);
//        categories=categories.stream()
//                .filter(category -> SystemConstants.STATUS_NORMAL.equals(category.getStatus())))
//                .collect(Collectors.toList());
        categories = categories.stream().
                filter(category -> SystemConstants.STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());
        //封装成vo,调用之前封装的工具类,BeanCopyUtils
        List<CategoryVo> categoryVos= BeanCopyUtils.copyBeanList(categories,CategoryVo.class);
        System.out.println(categoryVos);
        return ResponseResult.okResult(categoryVos);
    }
}

