package com.wangs.eduorder.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wangs.commonutils.R;
import com.wangs.eduorder.entity.Order;
import com.wangs.eduorder.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author wangs
 * @since 2021-06-01
 */
@RestController
@RequestMapping("/eduorder/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // 生成订单接口
    @PostMapping("createOrder")
    public R createOrder(@RequestBody Order order){
        // 后台强制设定价格为0.01元
        order.setTotalFee(new BigDecimal(0.01));
        order.setStatus(0);
        String order_id = orderService.createOrder(order); // 这里订单号不是生成的，是自己创建的
        return R.ok().data("orderId", order_id);
    }

    // 根据订单id查询订单信息
    @GetMapping("getOrderById/{orderId}")
    public R getOrderById(@PathVariable("orderId") String orderId){
        QueryWrapper<Order> wrapper = new QueryWrapper<Order>();
        wrapper.eq("order_no", orderId);
        Order order = orderService.getOne(wrapper);
        return R.ok().data("order", order);
    }

    // 查询用户是否购买过该课程
    @GetMapping("getUserCourseStatus/{userId}/{courseId}")
    public boolean getUserCourseStatus(@PathVariable String userId, @PathVariable String courseId){
        QueryWrapper<Order> wrapper = new QueryWrapper<Order>();
        wrapper.eq("course_id", courseId);
        wrapper.eq("member_id", userId);
        wrapper.eq("status", 1);
        int count = orderService.count(wrapper);
        if(count > 0){
            return true;
        }else{
            return false;
        }
    }

    // 查询用户是否已经创建订单
    @GetMapping("isCreatedOrder/{userId}/{courseId}")
    public R isCreatedOrder(@PathVariable String userId, @PathVariable String courseId){
        QueryWrapper<Order> wrapper = new QueryWrapper<Order>();
        wrapper.eq("course_id", courseId);
        wrapper.eq("member_id", userId);
        Order order = orderService.getOne(wrapper);

        return R.ok().data("order", order);
    }
}

