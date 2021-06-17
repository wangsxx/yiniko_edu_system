package com.wangs.eduorder.service;

import com.wangs.eduorder.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author wangs
 * @since 2021-06-01
 */
public interface PayLogService extends IService<PayLog> {

    Map createWxQRCode(String orderId);

    Map<String, String> getPayStatus(String orderId);

    void updateOrderStatus(Map<String, String> map);
}
