package com.light.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author lilepingstart
 * @creat 2023-07-01 21:01
 */
@Data
@Accessors(chain = true)
public class UserInfoVo {
    /**
     * 主键
     */
    private Long id;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String avatar;

    private String sex;

    private String email;


}

