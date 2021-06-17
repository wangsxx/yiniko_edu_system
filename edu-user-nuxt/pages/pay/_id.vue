<template>
    <div class="cart py-container">
        <!--主内容-->
        <div class="checkout py-container  pay">
            <div class="checkout-tit">
                <h4 class="fl tit-txt"><span class="success-icon"></span><span class="success-info">订单提交成功，请您及时付款！订单号：{{payObj.out_trade_no}}</span>
                </h4>
                <span class="fr"><em class="sui-lead">应付金额：</em><em class="orange money">￥{{payObj.total_fee}}</em></span>
                <div class="clearfix"></div>
            </div>
            <div class="checkout-steps">
                <div class="fl weixin">微信支付</div>
                <div class="fl sao">
                    <p class="red">请使用微信扫一扫。</p>
                    <div class="fl code">
                        <!-- <img id="qrious" src="~/assets/img/erweima.png" alt=""> -->
                        <!-- <qriously value="weixin://wxpay/bizpayurl?pr=R7tnDpZ" :size="338"/> -->
                        <qriously :value="payObj.code_url" :size="338"/>
                        <div class="saosao">
                            <p>请使用微信扫一扫</p>
                            <p>扫描二维码支付</p>
                        </div>

                    </div>

                </div>
                <div class="clearfix"></div>
                <!-- <p><a href="pay.html" target="_blank">> 其他支付方式</a></p> -->

            </div>
        </div>
    </div>
</template>

<script>
    import orderApi from "@/api/order"
    export default {
        name: "_id",
        data() {
            return{
                payObj: {
                    total_fee: "",
                    code_url: "",
                    out_trade_no: "",
                    course_id: ""
                },
                timer: "",  // 定时器，每隔几秒钟就执行一次
                orderId: "",
            }
        },
        created() {
            var orderId = this.$route.params.id
            this.orderId = orderId
            if(orderId){
                this.getCode(orderId)
            }
        },
        mounted() {
            this.timer = setInterval(() => {
                this.queryOrderStatus(this.orderId)
            }, 3000)
        },
        destroyed: function () {
            clearInterval(this.timer)
        },
        methods: {
            getCode(orderId){
                orderApi.createCode(orderId).then(response => {
                    this.payObj = response.data.data
                    console.log(this.payObj)
                })
            },
            queryOrderStatus(orderId){
                orderApi.queryOrderStatus(orderId).then(response => {
                    console.log(response)
                    if (response.data.success){
                        // 支付成功则返回课程详情页面
                        this.$message({
                            type: 'success',
                            message: '支付成功！'
                        });
                        // 清除定时器
                        clearInterval(this.timer)
                        // 跳转页面
                        this.$router.push({path: "/course/" + this.payObj.course_id})
                    }
                })
            }
        }
    }
</script>

<style>

</style>