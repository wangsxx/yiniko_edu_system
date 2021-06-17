package com.wangs.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @File : userMain.java
 * @Time : 2021/3/25 16:06
 * @Author: Wangs
 * @Decs :
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.wangs"})
public class userMain {
    public static void main(String[] args) {
        SpringApplication.run(userMain.class, args);
    }
}
