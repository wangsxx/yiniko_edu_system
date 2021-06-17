package com.wangs.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @File : OssMain.java
 * @Time : 2021/2/3 14:01
 * @Author: Wangs
 * @Decs :
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = {"com.wangs"})
@EnableDiscoveryClient
public class OssMain {
    public static void main(String[] args) {
        SpringApplication.run(OssMain.class, args);
    }
}
