package com.wangs.msm.service;

import java.util.Map;

/**
 * @File : MsmService.java
 * @Time : 2021/3/25 12:11
 * @Author: Wangs
 * @Decs :
 */
public interface MsmService {
    boolean send(Map<String, Object> map, String phone);
}
