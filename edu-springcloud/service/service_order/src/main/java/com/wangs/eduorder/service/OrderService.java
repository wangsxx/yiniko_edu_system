package com.wangs.eduorder.service;

import com.wangs.eduorder.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author wangs
 * @since 2021-06-01
 */
public interface OrderService extends IService<Order> {

    String createOrder(Order order);
}
