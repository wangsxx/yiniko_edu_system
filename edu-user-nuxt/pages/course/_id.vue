<template>
    <div id="aCoursesList" class="bg-fa of">
        <!-- /课程详情 开始 -->
        <section class="container">
            <section class="path-wrap txtOf hLh30">
                <a href="/index" title class="c-999 fsize14">首页</a>
                \
                <a href="/course" title class="c-999 fsize14">课程列表</a>
                \
                <span class="c-333 fsize14">{{courseInfo.title}}</span>
            </section>
            <div>
                <article class="c-v-pic-wrap" style="height: 357px;">
                    <section class="p-h-video-box" id="videoPlay">
                        <img :src="courseInfo.cover" :alt="courseInfo.title" class="dis c-v-pic" style="width: 640px;height: 357px">
                    </section>
                </article>
                <aside class="c-attr-wrap">
                    <section class="ml20 mr15">
                        <h2 class="hLh30 txtOf mt15">
                            <span class="c-fff fsize24">{{courseInfo.title}}</span>
                        </h2>
                        <section class="c-attr-jg">
                            <span class="c-fff">价格：</span>
                            <b class="c-yellow" style="font-size:24px;">￥{{courseInfo.price}}</b>
                        </section>
                        <section class="c-attr-mt c-attr-undis">
                            <span class="c-fff fsize14">主讲： {{teacherInfo.name}}&nbsp;&nbsp;&nbsp;</span>
                        </section>
                        <section class="c-attr-mt of">
              <span class="ml10 vam">
                <em class="icon18 scIcon"></em>
                <a class="c-fff vam" title="收藏" href="#" >收藏</a>
              </span>
                        </section>
                        <section class="c-attr-mt" v-if="courseInfo.price === 0 || isBuy">
                            <a href="#kcdg" title="立即观看" class="comm-btn c-btn-3">立即观看</a>
                        </section>
                        <section class="c-attr-mt" v-else>
                            <a href="javascript: void(0)" title="立即购买" class="comm-btn c-btn-3" @click="createOrder">立即购买</a>
                        </section>
                    </section>
                </aside>
                <aside class="thr-attr-box">
                    <ol class="thr-attr-ol ">
                        <li>
                            <p>&nbsp;</p>
                            <aside>
                                <span class="c-fff f-fM">购买数</span>
                                <br>
                                <h6 class="c-fff f-fM mt10">{{courseInfo.buyCount}}</h6>
                            </aside>
                        </li>
                        <li>
                            <p>&nbsp;</p>
                            <aside>
                                <span class="c-fff f-fM">课时数</span>
                                <br>
                                <h6 class="c-fff f-fM mt10">{{courseInfo.lessonNum}}</h6>
                            </aside>
                        </li>
                        <li>
                            <p>&nbsp;</p>
                            <aside>
                                <span class="c-fff f-fM">浏览数</span>
                                <br>
                                <h6 class="c-fff f-fM mt10">{{courseInfo.viewCount}}</h6>
                            </aside>
                        </li>
                    </ol>
                </aside>
                <div class="clear"></div>
            </div>
            <!-- /课程封面介绍 -->
            <div class="mt20 c-infor-box">
                <article class="fl col-7">
                    <section class="mr30">
                        <div class="i-box">
                            <div>
                                <section id="c-i-tabTitle" class="c-infor-tabTitle c-tab-title">
                                    <a name="c-i" class="current" title="课程详情">课程详情</a>
                                </section>
                            </div>
                            <article class="ml10 mr10 pt20">
                                <div>
                                    <h6 class="c-i-content c-infor-title">
                                        <span>课程介绍</span>
                                    </h6>
                                    <div class="course-txt-body-wrap">
                                        <section class="course-txt-body">
                                            <p>
                                                {{courseIntro}}
                                            </p>
                                        </section>
                                    </div>
                                </div>
                                <!-- /课程介绍 -->
                                <div class="mt50" id="kcdg">
                                    <h6 class="c-g-content c-infor-title">
                                        <span>课程大纲</span>
                                    </h6>
                                    <section class="mt20">
                                        <div class="lh-menu-wrap">
                                            <menu id="lh-menu" class="lh-menu mt10 mr10">
                                                <ul>
                                                    <!-- 文件目录 -->
                                                    <li class="lh-menu-stair" v-for="chapter in chapterVideo" :key="chapter.id">
                                                        <a href="javascript: void(0)" :title="chapter.title" class="current-1">
                                                            <em class="lh-menu-i-1 icon18 mr10"></em>{{chapter.title}}
                                                        </a>
                                                        <ol class="lh-menu-ol" style="display: block;">
                                                            <li class="lh-menu-second ml30" v-for="video in chapter.children" :key="video.id">
                                                                <a href="javascript: void(0)" @click="playVideo(video)" v-if="courseInfo.price === 0 || video.isFree || isBuy">
                                                                    <span class="fr" v-if="video.isFree">
                                                                    <i class="free-icon vam mr10" >免费试听</i>
                                                                  </span>
                                                                    <span class="fr" v-else-if="courseInfo.price === 0">
                                                                    <i class="free-icon vam mr10">免费课程</i>
                                                                  </span>
                                                                    <em class="lh-menu-i-2 icon16 mr5">&nbsp;</em>{{video.title}}
                                                                </a>
                                                                <a href="javascript: void(0)" @click="createOrder" v-else>
                                                                    <em class="lh-menu-i-2 icon16 mr5">&nbsp;</em>{{video.title}}
                                                                </a>
                                                            </li>
                                                        </ol>
                                                    </li>
                                                </ul>
                                            </menu>
                                        </div>
                                    </section>
                                </div>

                                <!-- 课程评论 -->
                                <div>
                                    <h6 class="c-i-content c-infor-title">
                                        <span>课程评论</span>
                                    </h6>
                                    <div class="course-txt-body-wrap">
                                        <el-input type="textarea" v-model="userComment" style="margin-top: 20px"></el-input>
                                        <div style="text-align: center">
                                            <el-button type="primary" round style="margin-top: 10px;" @click="submitComment">提交评论</el-button>
                                            <el-button type="info" round @click="resetComment">重置评论</el-button>
                                        </div>
                                        <!-- /无数据提示 开始-->
                                        <section v-if="commentList==''" class="no-data-wrap">
                                            <em class="icon30 no-data-ico">&nbsp;</em>
                                            <span class="c-666 fsize14 ml10 vam">没有相关数据，小编正在努力整理中...</span>
                                        </section>

                                        <section v-else class="course-txt-body">
                                            <ul>
                                                <li v-for="comment in commentList" :key="comment.id" class="h-r-user" style="font-family:Arial, Helvetica, sans-serif; margin-top: 15px">
                                                    <img :src="comment.avatar"
                                                         width="30" height="30"
                                                         alt
                                                         class="vam picImg" style="border-radius:15px;"/>
                                                    <span class="vam disIb" style="font-size: 17px">{{comment.nickname}}</span>
                                                    <p class="lh-menu-second ml40" style="font-size: 15px;margin-top: 10px">{{comment.content}}
                                                        <span class="fr">
                                                            <i class="vam mr10" style="font-size: 10px">{{comment.gmtCreate}}</i>
                                                        </span>
                                                    </p>
                                                </li>
                                            </ul>
                                            <div>
                                                <div class="paging">
                                                    <!-- 分页条-->
                                                    <el-pagination :current-page="currentPage" :page-size="limit" :total="total"
                                                                   background layout="total, prev, pager, next, jumper" style="padding: 30px 0; text-align: center"
                                                                   @current-change="getCourseComment">
                                                    </el-pagination>
                                                </div>
                                            </div>
                                        </section>

                                    </div>
                                </div>
                                <!-- /课程评论 -->
                                <!-- /课程大纲 -->
                            </article>
                        </div>
                    </section>
                </article>
                <aside class="fl col-3">
                    <div class="i-box">
                        <div>
                            <section class="c-infor-tabTitle c-tab-title">
                                <a title href="javascript:void(0)">主讲讲师</a>
                            </section>
                            <section class="stud-act-list">
                                <ul style="height: auto;">
                                    <li>
                                        <div class="u-face">
                                            <a :href="'/teacher/'+teacherInfo.id">
                                                <img :src="teacherInfo.avatar" width="50" height="50" alt>
                                            </a>
                                        </div>
                                        <section class="hLh30 txtOf">
                                            <a class="c-333 fsize16 fl" :href="'/teacher/'+teacherInfo.id">{{teacherInfo.name}}</a>
                                        </section>
                                        <section class="hLh20 txtOf">
                                            <span class="c-999">
                                                {{teacherInfo.career}}&nbsp;&nbsp;{{teacherInfo.level==1? "高级讲师":"首席讲师"}}
                                            </span>
                                        </section>
                                    </li>
                                </ul>
                            </section>
                        </div>
                    </div>
                </aside>
                <div class="clear"></div>
            </div>
        </section>
        <!-- /课程详情 结束 -->

        <el-dialog title="视频播放" :visible.sync="videoDialogVisible" @opened="handleOpened()" @close="handleClose()">
            <div>
                {{this.playedVideo.title}}
                {{this.playedVideo.id}}
                <!-- 阿里云视频播放器样式 -->
                <link rel="stylesheet" href="https://g.alicdn.com/de/prismplayer/2.8.1/skins/default/aliplayer-min.css" >
                <!-- 阿里云视频播放器脚本 -->
                <script charset="utf-8" type="text/javascript" src="https://g.alicdn.com/de/prismplayer/2.8.1/aliplayer-min.js" />
                <!-- 定义播放器dom -->
                <div id="J_prismPlayer" class="prism-player" />
            </div>
        </el-dialog>
    </div>
</template>

<script>
    import courseApi from '@/api/course'
    import commentApi from '@/api/comment'
    import orderApi from '@/api/order'
    import cookie from "js-cookie";
    export default {
        data(){
            return {
                courseInfo: {
                    id: "",
                    cover: "",
                    title: "",
                    price: "",
                    lessonNum: "",
                    buyCount: "",
                    viewCount: ""
                },
                courseIntro: "",
                teacherInfo: {
                    id: "",
                    name: "",
                    intro: "",
                    career: "",
                    level: "",
                    avatar: ""
                },
                // 视频播放字段
                chapterVideo: [],
                videoDialogVisible: false,
                playedVideo: "",
                playAuth: "",
                player: "",
                // 评论字段
                userComment: "",
                // 分页相关
                currentPage: 1,
                limit: 2,
                total: 0,
                commentList: [],
                // 用户是否购买过该课程
                isBuy: false
            }
        },
        created() {
            var courseId = this.$route.params.id
            if(courseId){
                this.getCourseDetail(courseId)
                this.courseInfo.id = courseId
                this.getCourseComment()
                this.queryUserIsBuy()
            }
        },
        methods: {
            getCourseDetail(id){
                courseApi.courseDetail(id).then(response=>{
                    if(response.data.success){
                        this.courseInfo = response.data.data.courseInfo
                        this.courseIntro = response.data.data.courseIntro.description
                        this.teacherInfo = response.data.data.teacherInfo
                        this.chapterVideo = response.data.data.chapterVideo
                        console.log(this.courseInfo)
                    }else{
                        // courseId不存在或错误
                    }
                })
            },
            // 查询用户是否购买过该课程
            queryUserIsBuy(){
                var userStr = cookie.get("wangs_user")
                if(userStr){
                    var user = JSON.parse(userStr)
                    orderApi.queryUserIsBuy(user.id, this.courseInfo.id).then(response =>{
                        this.isBuy = response.data
                    })
                }
            },
            playVideo(video){
                this.playedVideo = video
                courseApi.getVideoPlayAuth(this.playedVideo.videoSourceId).then(response =>{
                    this.playAuth = response.data.data.playAuth
                    this.videoDialogVisible = true
                })
            },
            handleOpened(){
                this.player = new Aliplayer({
                    id: 'J_prismPlayer',
                    vid: this.playedVideo.videoSourceId, // 视频id
                    playauth: this.playAuth, // 播放凭证
                    encryptType: '1', // 如果播放加密视频，则需设置encryptType=1，非加密视频无需设置此项
                    width: '100%',
                    height: '500px',
                    // 以下可选设置
                    cover: 'http://guli.shop/photo/banner/1525939573202.jpg', // 封面
                    qualitySort: 'asc', // 清晰度排序

                    mediaType: 'video', // 返回音频还是视频
                    autoplay: false, // 自动播放
                    isLive: false, // 直播
                    rePlay: false, // 循环播放
                    preload: true,
                    controlBarVisibility: 'hover', // 控制条的显示方式：鼠标悬停
                    useH5Prism: true, // 播放器类型：html5
                }, function(player) {
                    console.log('播放器创建成功')
                })
            },
            handleClose(){
                this.player.dispose()
                this.playAuth = ""
                this.playedVideo = ""
                this.videoDialogVisible = false
            },
            // 查看评论+分页
            getCourseComment(page=1){
                this.currentPage = page
                commentApi.pageComment(this.courseInfo.id, this.currentPage, this.limit)
                    .then(response => {
                        // 请求成果，返回结果
                        this.commentList = response.data.data.records
                        this.total = response.data.data.total
                })
            },
            submitComment(){
                var userStr = cookie.get("wangs_user")
                // 转换为json对象
                if(userStr){
                    // 判断内容是否为空
                    if(this.userComment === ""){
                        this.$message({
                            message: '请输入内容！',
                            type: 'error'
                        });
                    }else{
                        var user = JSON.parse(userStr)
                        var commentEntity = {
                            courseId: this.courseInfo.id,
                            teacherId: this.teacherInfo.id,
                            memberId: user.id,
                            nickname: user.nickname,
                            avatar: user.avatar,
                            content: this.userComment
                        }
                        commentApi.submitComment(commentEntity).then(response => {
                            if(response.data.success){
                                this.$message({
                                    message: '评论成功！',
                                    type: 'success'
                                });
                                this.userComment = ""
                                this.getCourseComment()
                            }
                        })
                    }
                }else{
                    this.$message({
                        message: '用户未登录，请先登录！',
                        type: 'warning'
                    });
                }
            },
            resetComment(){
                this.userComment = ""
            },
            // 订单相关
            createOrder(){
                // 判断用户是否登录
                var userStr = cookie.get("wangs_user")
                // 转换为json对象
                if(userStr){
                    var user = JSON.parse(userStr)
                    // 判断是否已经创建订单，而未支付
                    orderApi.isCreatedOrder(user.id, this.courseInfo.id).then(response => {
                        console.log(response)
                        // 如果order对象不为空
                        if(response.data.data.order){
                            // 已经创建对象，直接跳转
                            this.$message({
                                type: 'info',
                                message: '已存在订单'
                            });
                            this.$router.push({path:'/orders/'+response.data.data.order.orderNo})
                        }else{
                            this.$confirm('当前课程或视频未购买, 是否确定创建购买订单?', '提示', {
                                confirmButtonText: '确定',
                                cancelButtonText: '取消',
                                type: 'warning'
                            }).then(() => {
                                var order = {
                                    courseId: this.courseInfo.id,
                                    courseTitle: this.courseInfo.title,
                                    courseCover: this.courseInfo.cover,
                                    teacherName: this.teacherInfo.name,
                                    memberId: user.id,
                                    nickname: user.nickname,
                                    mobile: user.mobile,
                                    payType: 1   // 支付类型（1：微信 2：支付宝）
                                }
                                orderApi.createOrder(order).then(response => {
                                    if(response.data.success){
                                        this.$message({
                                            type: 'success',
                                            message: '订单创建成功！'
                                        });
                                        this.$router.push({path:'/orders/'+response.data.data.orderId})
                                    }
                                })
                            }).catch(() => {
                                this.$message({
                                    type: 'info',
                                    message: '已取消'
                                });
                            });
                        }
                    })
                }else{
                    this.$message({
                        message: '用户未登录，请先登录！',
                        type: 'warning'
                    });
                }
            }
        }
    };
</script>
