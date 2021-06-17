package com.wangs.msm.controller;

import com.wangs.commonutils.R;
import com.wangs.msm.service.MsmService;
import com.wangs.msm.utils.RandomUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @File : MsmController.java
 * @Time : 2021/3/25 12:11
 * @Author: Wangs
 * @Decs :
 */
@RestController
@RequestMapping("edumsm/msm/")
@CrossOrigin
public class MsmController {

    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;   // 通过redis完成过期时间的设置

    // 发送短信方法
    @GetMapping("sendMsm/{phone}")
    public R sendMsm(@PathVariable String phone){
        // 从redis中获取验证码，如果获取到直接返回
        String code = redisTemplate.opsForValue().get(phone);
        if(!StringUtils.isEmpty(code)) return R.ok();

        // 验证码是由我们自己生成，发送给阿里云发送短信
        code = RandomUtil.getSixBitRandom(); // 工具类，生成六位随机数
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", code);
        System.out.println(code);
        boolean isSend =  msmService.send(map, phone);
        if(isSend){
            // 如果为空，将code放到redis中，并设置过期时间
            redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
            return R.ok();
        }else{
            return R.error().message("短信发送失败！");
        }
    }

}
