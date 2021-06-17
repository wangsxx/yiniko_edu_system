package com.wangs.eduservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @File : EduServiceMain.java
 * @Time : 2021/1/26 14:59
 * @Author: Wangs
 * @Decs :
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.wangs"})
@EnableDiscoveryClient
@EnableFeignClients
public class EduServiceMain {
    public static void main(String[] args) {
        SpringApplication.run(EduServiceMain.class, args);
    }
}
