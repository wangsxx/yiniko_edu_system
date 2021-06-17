<template>
    <div>
        <div>
            <swiper :auto-update="true" ref="mySwiper" :options="swiperOption">
                <swiper-slide v-for="banner in bannerList" :key="banner.id" style="background: #040B1B;">
                    <a :href="banner.linkUrl">
                        <img :src="banner.imageUrl" :alt="banner.title">
                    </a>
                </swiper-slide>
                <div class="swiper-pagination swiper-pagination-white"></div>
                <div class="swiper-button-prev swiper-button-white" slot="button-prev" @click="prev"></div>
                <div class="swiper-button-next swiper-button-white" slot="button-next" @click="next"></div>
            </swiper>
        </div>

        <div id="aCoursesList">
        <!-- 网校课程 开始 -->
        <div>
            <section class="container">
                <header class="comm-title">
                    <h2 class="tac">
                        <span class="c-333">热门课程</span>
                    </h2>
                </header>
                <div>
                    <article class="comm-course-list">
                        <ul class="of" id="bna">
                            <li v-for="course in courseList" :key="course.id">
                                <div class="cc-l-wrap">
                                    <section class="course-img">
                                        <img
                                                :src="course.cover"
                                                class="img-responsive"
                                                :alt="course.title"
                                                style="height: 180px"
                                        >
                                        <div class="cc-mask">
                                            <a :href="'/course/'+course.id" title="开始学习" class="comm-btn c-btn-1">开始学习</a>
                                        </div>
                                    </section>
                                    <h3 class="hLh30 txtOf mt10">
                                        <a :href="'/course/'+course.id" :title="course.title" class="course-title fsize18 c-333">{{course.title}}</a>
                                    </h3>
                                    <section class="mt10 hLh20 of">
                                          <span class="fr jgTag bg-green" v-if="course.price == 0">
                                            <i class="c-fff fsize12 f-fA">免费</i>
                                          </span>
                                          <span class="fl jgAttr c-ccc f-fA">
                                            <i class="c-999 f-fA">{{course.viewCount}}人浏览</i>
                                            |
                                            <i class="c-999 f-fA">{{course.buyCount}}人学习</i>
                                          </span>
                                    </section>
                                </div>
                            </li>
                        </ul>
                        <div class="clear"></div>
                    </article>
                    <section class="tac pt20">
                        <a href="/course" title="全部课程" class="comm-btn c-btn-2">全部课程</a>
                    </section>
                </div>
            </section>
        </div>
        <!-- /网校课程 结束 -->
        <!-- 网校名师 开始 -->
        <div>
            <section class="container">
                <header class="comm-title">
                    <h2 class="tac">
                        <span class="c-333">名师大咖</span>
                    </h2>
                </header>
                <div>
                    <article class="i-teacher-list">
                        <ul class="of">
                            <li v-for="teacher in teacherList" :key="teacher.id">
                                <section class="i-teach-wrap">
                                    <div class="i-teach-pic">
                                        <a :href="'/teacher/'+teacher.id" :title="teacher.name">
                                            <img :alt="teacher.name" :src="teacher.avatar">
                                        </a>
                                    </div>
                                    <div class="mt10 hLh30 txtOf tac">
                                        <a :href="'/teacher/'+teacher.id" :title="teacher.name" class="fsize18 c-666">{{teacher.name}}</a>
                                    </div>
                                    <div class="hLh30 txtOf tac">
                                        <span class="fsize14 c-999">{{teacher.intro}}</span>
                                    </div>
                                    <div class="mt15 i-q-txt">
                                        <p class="c-999 f-fA">{{teacher.career}}</p>
                                    </div>
                                </section>
                            </li>
                        </ul>
                        <div class="clear"></div>
                    </article>
                    <section class="tac pt20">
                        <a href="/teacher" title="全部讲师" class="comm-btn c-btn-2">全部讲师</a>
                    </section>
                </div>
            </section>
        </div>
        <!-- /网校名师 结束 -->
    </div>
    </div>
</template>

<script>
    import { Swiper, SwiperSlide } from 'vue-awesome-swiper'
    import banner from "@/api/banner";
    import index from "@/api/index";

    export default {
        data() {
            return {
                swiperOption: {
                    loop: true,
                    autoplay: {
                        delay: 2000,
                        stopOnLastSlide: false,
                        disableOnInteraction: false,
                    },
                    //配置分页
                    pagination: {
                        el: '.swiper-pagination'//分页的dom节点
                    },
                    //配置导航
                    navigation: {
                        nextEl: '.swiper-button-next',//下一页dom节点
                        prevEl: '.swiper-button-prev'//前一页dom节点
                    }
                },
                bannerList: [],
                courseList: [],
                teacherList: [],
                courseNum: 8,  // 首页的课程数量
                teacherNum: 4  // 首页的讲师数量
            }
        },
        components: {
            Swiper,
            SwiperSlide
        },
        created() {
            this.getBannerList()
            this.getPopularCourse()
            this.getPopularTeacher()
        },
        methods: {
            prev() {
                this.$refs.mySwiper.$swiper.slidePrev()
            },
            next() {
                this.$refs.mySwiper.$swiper.slideNext()
            },
            getBannerList(){
                banner.getBannerList().then(response => {
                    this.bannerList = response.data.data.bannerList
                })
            },
            getPopularCourse(){
                index.getPopularCourse(this.courseNum).then(response => {
                    this.courseList = response.data.data.courseList
                })
            },
            getPopularTeacher(){
                index.getPopularTeacher(this.teacherNum).then(response => {
                    this.teacherList = response.data.data.teacherList
                })
            }
        }
    }
</script>