package com.light.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.light.constants.SystemConstants;
import com.light.domin.ResponseResult;
import com.light.domin.entity.Article;
import com.light.mapper.ArticleMapper;
import com.light.service.ArticleService;
import com.light.service.CategoryService;
import com.light.utils.BeanCopyUtils;
import com.light.vo.ArticleListVo;
import com.light.vo.HotArticleVo;
import com.light.vo.PageVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author lilepingstart
 * @creat 2023-06-17 20:53
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private CategoryService categoryService;

    @Override
    public ResponseResult hotArticleList() {
        //定义一个封装查询条件
        LambdaQueryWrapper<Article> queryWrapper=new LambdaQueryWrapper<>();
        //必须是正式文章
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //按照浏览量排序
        queryWrapper.orderByDesc(Article::getViewCount);
        //创建分页对象，指定当前页数和每页记录数
        Page<Article> page=new Page(1,10);
        //调用page方法进行分页查询
        page(page,queryWrapper);
        //调用page.getre,返回热门文章列表
        List<Article> articles = page.getRecords();
        //创建热门文章列表
        List<HotArticleVo> hotArticleVos=new ArrayList<HotArticleVo>();
        //使用SpringBoot中的Bean拷贝方法进行封装，遍历articles列表，一个一个拷贝到hotaricle中去
        // (VO拷贝的前提是字段名和类型相同
        hotArticleVos= BeanCopyUtils.copyBeanList(articles,HotArticleVo.class);

//        for (Article article:articles) {
//            HotArticleVo vo=new HotArticleVo();
//            BeanUtils.copyProperties(article,vo);//注意顺序
//            hotArticleVos.add(vo);
//        }
        return ResponseResult.okResult(hotArticleVos);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        //查询条件
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 如果 有categoryId 就要 查询时要和传入的相同
        lambdaQueryWrapper.eq(Objects.nonNull(categoryId)&&categoryId>0 ,Article::getCategoryId,categoryId);
        // 状态是正式发布的
        lambdaQueryWrapper.eq(Article::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
        // 对isTop进行降序
        lambdaQueryWrapper.orderByDesc(Article::getIsTop);

        //分页查询
        Page<Article> page = new Page<>(pageNum,pageSize);
        page(page,lambdaQueryWrapper);

        List<Article> articles = page.getRecords();
        //查询categoryName
        articles.stream()
                .map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()))
                .collect(Collectors.toList());
        //articleId去查询articleName进行设置
//        for (Article article : articles) {
//            Category category = categoryService.getById(article.getCategoryId());
//            article.setCategoryName(category.getName());
//        }

        //封装查询结果
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVo.class);

        PageVo pageVo = new PageVo(articleListVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }
}
