import request from '@/utils/request'

export default {
    createOrder(order){
        return request({
            url: `/eduorder/order/createOrder`,
            method: "post",
            data: order
        })
    },
    getOrderById(orderId){
        return request({
            url: `/eduorder/order/getOrderById/${orderId}`,
            method: "get"
        })
    },
    // 生成二维码
    createCode(orderId){
        return request({
            url: `/eduorder/pay-log/createWxQRCode/${orderId}`,
            method: "get"
        })
    },
    // 查询状态
    queryOrderStatus(orderId){
        return request({
            url: `/eduorder/pay-log/getPayStatus/${orderId}`,
            method: "get"
        })
    },
    // 查询用户是否购买课程
    queryUserIsBuy(userId, courseId){
        return request({
            url: `/eduorder/order/getUserCourseStatus/${userId}/${courseId}`,
            method: "get"
        })
    },
    // 查询是否已经创建课程
    // 查询用户是否购买课程
    isCreatedOrder(userId, courseId){
        return request({
            url: `/eduorder/order/isCreatedOrder/${userId}/${courseId}`,
            method: "get"
        })
    }

}