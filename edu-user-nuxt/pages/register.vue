<template>
    <div class="sign">
        <div class="main">
            <div class="title">
                <a href="/login">登录</a>
                <span>·</span>
                <a class="active" href="/register">注册</a>
            </div>

            <div class="sign-up-container">
                <el-form ref="params" :model="params" :rules="rules" label-width="0">
                    <el-form-item prop="nickname">
                        <el-input v-model="params.nickname" placeholder="昵称" style=""></el-input>
                    </el-form-item>
                    <el-form-item prop="mobile">
                        <el-input v-model="params.mobile" placeholder="手机号" style=""></el-input>
                    </el-form-item>
                    <el-form-item prop="password">
                        <el-input v-model="params.password" placeholder="请输入密码" show-password></el-input>
                    </el-form-item>
                    <el-form-item >
                        <el-input placeholder="验证码" v-model="params.code">
                            <el-button slot="append" :value="codeTest" @click="getCodeFun('params')" :disabled="disable">{{codeTest}}</el-button>
                        </el-input>
                    </el-form-item>

                    <el-button type="primary" @click="submitRegister('params')" round style="margin-top: 20px; width: 100%" >立即注册</el-button>
                </el-form>
                <!-- 更多注册方式 -->
                <div class="more-sign">
                    <h6>社交帐号直接注册</h6>
                    <ul>
                        <li><a id="weixin" class="weixin" target="_blank" href="http://huaan.free.idcfengye.com/api/ucenter/wx/login"><i
                                class="iconfont icon-weixin"/></a></li>
                        <li><a id="qq" class="qq" target="_blank" href="#"><i class="iconfont icon-qq"/></a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import '~/assets/css/sign.css'
    import '~/assets/css/iconfont.css'

    import registerApi from '@/api/register'

    export default {
        data() {
            return {
                params: { //封装注册输入数据
                    mobile: '',
                    code: '',  //验证码
                    nickname: '',
                    password: ''
                },
                disable: false,      //是否发送验证码
                second: 60,        //倒计时间
                codeTest: '获取验证码',
                rules:{
                    nickname: [
                        { required: true, message: '请输入昵称', trigger: 'blur' }
                    ],
                    mobile: [
                        { required: true, message: "请输入手机号码", trigger: "blur" },
                        {
                            pattern: /^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\d{8}$/,
                            message: "请输入正确的手机号码"
                        }
                    ],
                    password: [
                        { required: true, message: '请输入密码', trigger: 'blur' },
                    ]
                }
            }
        },
        methods: {
            //注册提交的方法
            submitRegister(params) {
                this.$refs[params].validate((valid) => {
                    if (valid) {
                        registerApi.register(this.params)
                            .then(response => {
                                if(response.data.success){
                                    //提示注册成功
                                    this.$message({
                                        type: 'success',
                                        message: "注册成功"
                                    })
                                    //跳转登录页面
                                    this.$router.push({path:'/login'})
                                }else{
                                    this.$message({
                                        type: 'error',
                                        message: response.data.message
                                    })
                                }

                            })
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },

            timeDown() {
                let result = setInterval(() => {
                    --this.second;
                    this.codeTest = this.second
                    if (this.second < 1) {
                        clearInterval(result);
                        this.disable = false;
                        //this.disabled = false;
                        this.second = 60;
                        this.codeTest = "获取验证码"
                    }
                }, 1000);

            },
            //通过输入手机号发送验证码
            getCodeFun(params) {
                // 判断信息输入是否完全
                this.$refs[params].validate((valid) => {
                    if (valid) {
                        registerApi.sendCode(this.params.mobile)
                            .then(response => {
                                this.disable = true
                                //调用倒计时的方法
                                this.timeDown()
                            })
                    } else {

                    }
                })
            },
            // input无法输入解决方法
            onInput(){
                this.$forceUpdate();
            }
        }
    }
</script>