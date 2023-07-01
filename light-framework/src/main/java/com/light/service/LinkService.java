package com.light.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.light.domin.ResponseResult;
import com.light.domin.entity.Link;


/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2023-07-01 17:48:20
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();
}

