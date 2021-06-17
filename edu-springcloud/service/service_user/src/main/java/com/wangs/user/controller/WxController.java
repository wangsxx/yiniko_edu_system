package com.wangs.user.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.wangs.commonutils.JwtUtils;
import com.wangs.user.entity.UcenterMember;
import com.wangs.user.service.UcenterMemberService;
import com.wangs.user.util.HttpClientUtils;
import com.wangs.user.util.WxUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * @File : WxController.java
 * @Time : 2021/5/26 11:14
 * @Author: Wangs
 * @Decs :
 */

@Controller  // 这里要用Controller，不用restController
@RequestMapping("/api/ucenter/wx")
public class WxController {

    @Autowired
    private UcenterMemberService ucenterMemberService;

    // 生成微信登录二维码
    @GetMapping("wxLogin")
    public String getWxCode(){
        // %s相当于占位符
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect?" +
                "appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s#wechat_redirect";
        // 对redirct_url进行编码
        String redirectUrl = WxUtils.WX_OPEN_REDIRECT_URL;
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = String.format(
                baseUrl,
                WxUtils.WX_OPEN_APP_ID,
                redirectUrl,
                "atguigu"
        );
        // 请求微信地址
        return "redirect:"+url;
    }

    // 回调方法
    @GetMapping("callback")
    public String callback(String code, String state){
        //根据code获取access_token和openid
        String access_url = "https://api.weixin.qq.com/sns/oauth2/access_token?" +
                "appid=" + WxUtils.WX_OPEN_APP_ID +
                "&secret=" + WxUtils.WX_OPEN_APP_SECRET +
                "&code=" + code +
                "&grant_type=authorization_code";
        // 根据openid和access_token获得用户信息
        String userInfo_url = "https://api.weixin.qq.com/sns/userinfo?" +
                "access_token=%s" +
                "&openid=%s";
        // 最后返回的jwt字符串
        String jwtToken = "";
        // 使用httpclient发送请求，得到返回结果
        try{
            String accessToken = HttpClientUtils.get(access_url);
            // 将json转为map对象
            Gson gson = new Gson();
            HashMap access_map = gson.fromJson(accessToken, HashMap.class);
            String access_token = (String) access_map.get("access_token");
            String openid = (String) access_map.get("openid");
            // 将用户信息添加到数据库，首先判断是否已有数据，根据openid判断
            UcenterMember user = ucenterMemberService.getByOpenId(openid);
            if(user == null){
                // 获得url，获得用户信息
                userInfo_url = String.format(userInfo_url, access_token, openid);
                String userInfo = HttpClientUtils.get(userInfo_url);
                HashMap user_map = gson.fromJson(userInfo, HashMap.class);  // 得到用户信息
                String user_nickname = (String)user_map.get("nickname");
                String user_headimg = (String)user_map.get("headimgurl");

                // 如果为空，则插入数据库
                user = new UcenterMember();
                user.setNickname(user_nickname);
                user.setAvatar(user_headimg);
                user.setOpenid(openid);
                ucenterMemberService.save(user);
            }
            jwtToken = JwtUtils.getJwtToken(user.getId(), user.getNickname());
        }catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:http://localhost:3000?token="+jwtToken;
    }
}
