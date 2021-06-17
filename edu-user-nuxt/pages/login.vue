<template>
    <div class="sign">
        <div class="main">
            <div class="title">
                <a class="active" href="/login">登录</a>
                <span>·</span>
                <a href="/register">注册</a>
            </div>

            <div class="sign-up-container">

                <el-form ref="user" :model="user" :rules="rules" label-width="0">

                    <el-form-item prop="mobile">
                        <el-input v-model="user.mobile" placeholder="手机号" style=""></el-input>
                    </el-form-item>
                    <el-form-item prop="password">
                        <el-input v-model="user.password" placeholder="请输入密码" show-password></el-input>
                    </el-form-item>

                    <el-button type="primary" @click="submitLogin('user')" round style="margin-top: 20px; width: 100%" >立即登录</el-button>
                </el-form>

                <!-- 更多登录方式 -->
                <div class="more-sign">
                    <h6>社交帐号登录</h6>
                    <ul>
                        <li><a style="text-decoration: none"
                               href="http://localhost:9001/api/ucenter/wx/wxLogin">
                            <i class="iconfont icon-weixin"/></a></li>
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
    import loginApi from "../api/login";
    import cookie from 'js-cookie'
    import login from "../api/login";

    export default {
        data() {
            return {
                user: {
                    mobile: '',
                    password: ''
                },
                loginInfo: {},
                rules:{
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
        methods:{
            submitLogin(user){
                this.$refs[user].validate((valid) => {
                    if (valid) {
                        // 1. 调用接口返回token字符串
                        loginApi.login(this.user).then(response =>{
                            console.log(response)
                            if(response.data.success){
                                // 登录成功逻辑 获得token字符串，并设置cookie
                                // cookie名称、cookie值、cookie作用域
                                cookie.set("wangs_token", response.data.data.token, {domain: 'localhost'})
                                // 4. 调用获得用户信息接口
                                loginApi.getUserInfo().then(responseUser => {
                                    // 将返回用户信息放到Cookie中
                                    cookie.set("wangs_user", responseUser.data.data.user, {domain: 'localhost'})
                                    this.loginInfo = responseUser.data.data.user
                                })
                                window.location.href = "/"
                            }else{
                                this.$message({
                                    type: 'error',
                                    message: response.data.message
                                })
                            }
                        })
                    }else{
                        this.$message({
                            type: 'error',
                            message: "信息不全！"
                        })
                    }
                })
            }
        }
    }
</script>
<style>
   .el-form-item__error{
    z-index: 9999999;
  }
</style>