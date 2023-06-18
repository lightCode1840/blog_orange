package com.light.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lilepingstart
 * @creat 2023-06-18 17:44
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryVo {

    private Long id;

    //分类名
    private String name;
}