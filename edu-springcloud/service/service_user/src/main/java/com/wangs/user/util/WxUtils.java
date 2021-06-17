package com.wangs.user.util;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @File : WxUtils.java
 * @Time : 2021/5/26 10:44
 * @Author: Wangs
 * @Decs : 微信相关工具类
 */
@Component
public class WxUtils implements InitializingBean {

    @Value("${wx.open.appid}")
    private String appId;

    @Value("${wx.open.appsecret}")
    private String appSecret;

    @Value("${wx.open.redirecturl}")
    private String redirectUrl;

    public static String WX_OPEN_APP_ID;
    public static String WX_OPEN_APP_SECRET;
    public static String WX_OPEN_REDIRECT_URL;

    public void afterPropertiesSet() throws Exception {
        WX_OPEN_APP_ID = appId;
        WX_OPEN_APP_SECRET = appSecret;
        WX_OPEN_REDIRECT_URL = redirectUrl;
    }
}
