package com.wangs.msm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * @File : msmMain.java
 * @Time : 2021/3/25 12:10
 * @Author: Wangs
 * @Decs :
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan("com.wangs")
@CrossOrigin
public class msmMain {
    public static void main(String[] args) {
        SpringApplication.run(msmMain.class, args);
    }
}
