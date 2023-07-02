package com.light.service;

import com.light.domin.ResponseResult;
import com.light.domin.entity.User;

/**
 * @author lilepingstart
 * @creat 2023-07-01 18:39
 */
public interface BlogLoginService {
    public ResponseResult login(User user) ;
}
