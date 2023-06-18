package com.light.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lilepingstart
 * @creat 2023-06-18 12:25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotArticleVo {
    private Long id;
    //标题
    private String title;
    //访问量
    private Long viewCount;
}
