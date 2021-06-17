package com.wangs.eduorder.service.impl;

import com.wangs.eduorder.entity.Order;
import com.wangs.eduorder.mapper.OrderMapper;
import com.wangs.eduorder.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangs.eduorder.utils.OrderNoUtil;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author wangs
 * @since 2021-06-01
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    public String createOrder(Order order) {
        String order_id = OrderNoUtil.getOrderNo();
        order.setOrderNo(order_id);
        baseMapper.insert(order);
        return order_id;
    }
}
