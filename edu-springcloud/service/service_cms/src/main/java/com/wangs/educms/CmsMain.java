package com.wangs.educms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @File : CmsMail.java
 * @Time : 2021/3/18 11:55
 * @Author: Wangs
 * @Decs :
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.wangs"})
public class CmsMain {
    public static void main(String[] args) {
        SpringApplication.run(CmsMain.class, args);
    }

}
