package com.wangs.eduorder.controller;


import com.wangs.commonutils.R;
import com.wangs.eduorder.service.PayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author wangs
 * @since 2021-06-01
 */
@RestController
@RequestMapping("/eduorder/pay-log")
public class PayLogController {

    @Autowired
    private PayLogService payLogService;

    // 微信支付二维码生成接口
    @GetMapping("createWxQRCode/{orderId}")
    public R createWxQRCode(@PathVariable("orderId") String orderId){
        // 返回相关信息，包含二维码地址和其他信息
        Map map = payLogService.createWxQRCode(orderId);
        return R.ok().data(map);
    }

     // 查询订单支付状态，使用微信提供的方法
    @GetMapping("getPayStatus/{orderId}")
    public R getPayStatus(@PathVariable("orderId") String orderId){
        Map<String, String> map = payLogService.getPayStatus(orderId);
        if(map == null){
            return R.error().message("支付出错！");
        }
        if(map.get("trade_state").equals("SUCCESS")){
            // 向支付表中添加记录，并更新订单表中状态
            payLogService.updateOrderStatus(map);
            // 如果不为空，通过map获取订单状态
            return R.ok().message("支付成功！");
        }
        // 返回一直支付中的状态
        return R.error().message("支付中......");
    }

}

