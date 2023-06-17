package com.light;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lilepingstart
 * @creat 2023-06-17 16:57
 */

@SpringBootApplication
@MapperScan("com.light.mapper")
public class LightBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(LightBlogApplication.class,args);
    }

}
