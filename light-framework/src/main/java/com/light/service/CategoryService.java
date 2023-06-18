package com.light.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.light.domin.ResponseResult;
import com.light.domin.entity.Category;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2023-06-18 17:15:14
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();
}

