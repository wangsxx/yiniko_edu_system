package com.wangs.msm.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.wangs.msm.service.MsmService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @File : MsmServiceImpl.java
 * @Time : 2021/3/25 12:12
 * @Author: Wangs
 * @Decs :
 */
@Service
public class MsmServiceImpl implements MsmService {
    public boolean send(Map<String, Object> map, String phone) {
        // 判断手机号是否为十一位
        if(phone.length() != 11) return false;

        DefaultProfile profile = DefaultProfile.getProfile("default",
                "XXX", "XXX");
        IAcsClient client = new DefaultAcsClient(profile);

        // 设置参数
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");

        // 设置发送的相关参数
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("TemplateCode", "教育项目登录注册短信模板");
        request.putQueryParameter("SignName", "SMS_213770338");
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(map));


        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }


        return true;
    }
}
