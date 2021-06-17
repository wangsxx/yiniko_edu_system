package com.wangs.eduorder.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.wxpay.sdk.WXPayUtil;
import com.wangs.eduorder.entity.Order;
import com.wangs.eduorder.entity.PayLog;
import com.wangs.eduorder.mapper.PayLogMapper;
import com.wangs.eduorder.service.OrderService;
import com.wangs.eduorder.service.PayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangs.eduorder.utils.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author wangs
 * @since 2021-06-01
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {

    @Autowired
    private OrderService orderService;

    public Map createWxQRCode(String orderId) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            // 根据订单号，查询订单信息
            QueryWrapper<Order> wrapper = new QueryWrapper<Order>();
            wrapper.eq("order_no", orderId);
            Order order = orderService.getOne(wrapper);

            // 使用map设置生成二维码需要的参数
            Map m = new HashMap();
            m.put("appid","wx74862e0dfcf69954");
            m.put("mch_id", "1558950191");
            m.put("nonce_str", WXPayUtil.generateNonceStr());
            m.put("body", order.getCourseTitle()); //课程标题
            m.put("out_trade_no", orderId); //订单号
            m.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue()+"");
            m.put("spbill_create_ip", "127.0.0.1");
            m.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify\n");
            m.put("trade_type", "NATIVE");

            // 发送httpclient请求，传递参数xml格式，微信固定请求地址
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            client.setXmlParam(WXPayUtil.generateSignedXml(m, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);

            // 执行请求，返回请求结果
            client.post();
            String content_xml = client.getContent();  // 返回结果是xml格式
            Map resultMap = WXPayUtil.xmlToMap(content_xml);

            result.put("out_trade_no", orderId);
            result.put("course_id", order.getCourseId());
            result.put("total_fee", order.getTotalFee());
            result.put("result_code", resultMap.get("result_code"));  //返回二维码操作状态码
            result.put("code_url", resultMap.get("code_url"));        //二维码地址

        }catch (Exception e){

        }
        return result;
    }

    public Map<String, String> getPayStatus(String orderId) {
        HashMap<String, String> map = new HashMap<String, String>();
        Map<String, String> result = new HashMap<String, String>();
        map.put("appid","wx74862e0dfcf69954");
        map.put("mch_id", "1558950191");
        map.put("out_trade_no", orderId); //订单号
        map.put("nonce_str", WXPayUtil.generateNonceStr());

        // 发送httpclient请求
        HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
        try {
            client.setXmlParam(WXPayUtil.generateSignedXml(map, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();
            String xml = client.getContent();
            result = WXPayUtil.xmlToMap(xml);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 得到请求内容
        return result;
    }

    public void updateOrderStatus(Map<String, String> map) {
        if(map.get("return_code").equals("SUCCESS")
                && map.get("result_code").equals("SUCCESS")
                && map.get("trade_state").equals("SUCCESS")){

            // 如果成功支付就执行逻辑
            // 从map中获取订单号
            String order_id = map.get("out_trade_no");
            // 根据订单号，查询订单信息
            QueryWrapper<Order> wrapper = new QueryWrapper<Order>();
            wrapper.eq("order_no", order_id);
            Order order = orderService.getOne(wrapper);

            if(order.getStatus().intValue() == 0){
                order.setStatus(1);  // 1 表示支付成功
                orderService.updateById(order);
            }

            // 支付记录表中添加信息
            PayLog log = new PayLog();
            log.setOrderNo(order_id);
            SimpleDateFormat sdf  = new SimpleDateFormat("yyyyMMddHHmmss");
            try {
                log.setPayTime(sdf.parse(map.get("time_end")));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            log.setTotalFee(order.getTotalFee());
            log.setTransactionId(map.get("transaction_id"));
            log.setAttr(JSONObject.toJSONString(map));
            log.setPayType(1);
            log.setTradeState(map.get("trade_state"));

            // 查询是否已经添加，如果是则不添加
            QueryWrapper<PayLog> wrapper1 = new QueryWrapper<PayLog>();
            wrapper1.eq("order_no", order_id);

            PayLog payLog = baseMapper.selectOne(wrapper1);
            System.out.println(payLog);
            if(payLog == null){
                baseMapper.insert(log);
            }
        }
    }
}
