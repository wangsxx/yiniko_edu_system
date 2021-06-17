package com.wangs.eduservice.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @File : userClient.java
 * @Time : 2021/6/1 10:40
 * @Author: Wangs
 * @Decs :
 */
@FeignClient(name = "service-user", fallbackFactory = userFeedback.class)
@Component
public interface userClient {



}
