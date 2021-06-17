package com.wangs.eduservice.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @File : userClient.java
 * @Time : 2021/6/1 10:40
 * @Author: Wangs
 * @Decs :
 */
@FeignClient(name = "edu-order", fallback = orderFeedback.class)
@Component
public interface orderClient {

    @GetMapping("/eduorder/order/getUserCourseStatus/{userId}/{courseId}")
    public boolean getUserCourseStatus(@PathVariable("userId") String userId, @PathVariable("courseId") String courseId);
}
