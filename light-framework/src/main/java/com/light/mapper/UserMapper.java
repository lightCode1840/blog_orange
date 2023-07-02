package com.light.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.light.domin.entity.User;
import org.springframework.stereotype.Repository;


/**
 * 用户表(SysUser)表数据库访问层
 *
 * @author makejava
 * @since 2023-07-01 18:20:25
 */
@Repository(value = "userMapper")
public interface UserMapper extends BaseMapper<User> {

}

