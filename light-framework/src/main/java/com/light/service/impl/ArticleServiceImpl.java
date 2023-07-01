package com.light.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.light.constants.SystemConstants;
import com.light.domin.ResponseResult;
import com.light.domin.entity.Article;
import com.light.domin.entity.Category;
import com.light.mapper.ArticleMapper;
import com.light.service.ArticleService;
import com.light.service.CategoryService;
import com.light.utils.BeanCopyUtils;
import com.light.vo.ArticleDetailVo;
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
        //记得在article类中设置注解@Accessors(chain = true)
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

    //文章详情页面查询，查询出具体内容之后
    // 还需要根据分类id查询文章对应的分类名称，返回给前端进行展示
    @Override
    public ResponseResult getArticleDetail(Long id) {
        //根据id查询文章
        Article article=getById(id);
        //装换成VO
        ArticleDetailVo articleDetailVo=BeanCopyUtils.copyBean(article,ArticleDetailVo.class);
        //根据分类id查询
        Long categoryId=articleDetailVo.getCategoryId();
//        String categoryName=categoryService.getById(categoryId).getName();
        //避免空指针异常，不使用上述方法设置VO对象的分类名称
        Category category = categoryService.getById(categoryId);
        if(category!=null){
            articleDetailVo.setCategoryName(category.getName());
        }
        return ResponseResult.okResult(articleDetailVo);
    }
}
