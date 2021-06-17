package com.wangs.eduservice.feignClient;

import org.springframework.stereotype.Component;

/**
 * @File : userFeedback.java
 * @Time : 2021/6/1 10:41
 * @Author: Wangs
 * @Decs :
 */
@Component
public class orderFeedback implements orderClient{
    @Override
    public boolean getUserCourseStatus(String userId, String courseId) {
        return false;
    }
}
