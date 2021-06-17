package com.wangs.eduorder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @File : OrderMain.java
 * @Time : 2021/6/1 14:32
 * @Author: Wangs
 * @Decs :
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.wangs"})
public class OrderMain {
    public static void main(String[] args) {
        SpringApplication.run(OrderMain.class, args);
    }
}
