package com.wangs.video.util;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @File : ReadProperties.java
 * @Time : 2021/2/3 14:12
 * @Author: Wangs
 * @Decs :
 */
@Component
public class ReadProperties implements InitializingBean {
    // 读取配置文件内容
    @Value("${aliyun.oss.file.keyid}")
    private String keyId;

    @Value("${aliyun.oss.file.keysecret}")
    private String keySecret;

    // 定义公开常量
    public static String KEY_ID;
    public static String KEY_SECRET;

    public void afterPropertiesSet() throws Exception {
        KEY_ID = keyId;
        KEY_SECRET = keySecret;
    }
}
