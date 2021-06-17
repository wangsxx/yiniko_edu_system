package com.wangs.video;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @File : VideoMain.java
 * @Time : 2021/3/15 10:35
 * @Author: Wangs
 * @Decs :
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = {"com.wangs"})
@EnableDiscoveryClient
public class VideoMain {
    public static void main(String[] args) {
        SpringApplication.run(VideoMain.class, args);
    }
}
