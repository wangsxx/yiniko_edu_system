<template>
    <div id="aCoursesList" class="bg-fa of">
        <!-- /课程列表 开始 -->
        <section class="container">
            <header class="comm-title">
                <h2 class="fl tac">
                    <span class="c-333">全部课程</span>
                </h2>
            </header>
            <section class="c-sort-box">
                <section class="c-s-dl">
                    <dl>
                        <dt>
                            <span class="c-999 fsize14">课程类别</span>
                        </dt>
                        <dd class="c-s-dl-li">
                            <ul class="clearfix">
                                <li :class="{'current':(oneIndex==-1 && twoIndex==-1)}">
                                    <a title="全部" href="javascript:void(0)" @click="getAllCourse()" >全部</a>
                                </li>
                                <li v-for="(subject,index) in subjectList" :key="subject.id" :class="{'current':oneIndex==index}">
                                    <a :title="subject.title" href="javascript:void(0)" @click="getTwoSubject(subject, index)">{{subject.title}}</a>
                                </li>
                            </ul>
                        </dd>
                    </dl>
                    <dl v-if="twoSubjectList.length > 0">
                        <dt>
                            <span class="c-999 fsize14 clearfix">二级分类</span>
                        </dt>
                        <dd class="c-s-dl-li" >
                            <ul class="clearfix">
                                <li v-for="(twoSubject, index) in twoSubjectList" :key="twoSubject.id" :class="{'current':twoIndex==index}">
                                    <a :title="twoSubject.title" href="javascript:void(0)" @click="queryByTwoSubject(twoSubject.id, index)">{{twoSubject.title}}</a>
                                </li>
                            </ul>
                        </dd>
                    </dl>
                    <div class="clear"></div>
                </section>
                <div class="js-wrap">
                    <section class="fr">
            <span class="c-ccc">
              <i class="c-master f-fM">1</i>/
              <i class="c-666 f-fM">1</i>
            </span>
                    </section>
                    <section class="fl">
                        <ol class="js-tap clearfix">
                            <li :class="{'current bg-orange':sortNum==0}">
                                <a title="最新" href="javascript:void(0)" @click="orderCourse(0)">最新</a>
                            </li>
                            <li :class="{'current bg-orange':sortNum==1}">
                                <a title="价格" href="javascript:void(0)" @click="orderCourse(1)">价格&nbsp;
                                </a>
                            </li>
                            <li :class="{'current bg-orange':sortNum==2}">
                                <a title="关注度" href="javascript:void(0)" @click="orderCourse(2)">关注度</a>
                            </li>
                            <li :class="{'current bg-orange':sortNum==3}">
                                <a title="热销课程" href="javascript:void(0)" @click="orderCourse(3)">热销课程&nbsp;
                                </a>
                            </li>
                        </ol>
                    </section>
                </div>
                <div class="mt40">
                    <!-- /无数据提示 开始-->
                    <section v-if="courseList.length==0" class="no-data-wrap">
                        <em class="icon30 no-data-ico">&nbsp;</em>
                        <span class="c-666 fsize14 ml10 vam">没有相关数据，小编正在努力整理中...</span>
                    </section>
                    <!-- /无数据提示 结束-->

                    <article v-else class="comm-course-list">
                        <ul class="of" id="bna">
                            <li v-for="course in courseList" :key="course.id">
                                <div class="cc-l-wrap">
                                    <section class="course-img">
                                        <img :src="course.cover" class="img-responsive"
                                             :alt="course.title" style="width: 270px;height: 150px">
                                        <div class="cc-mask">
                                            <a :href="'/course/'+course.id" title="开始学习" class="comm-btn c-btn-1">开始学习</a>
                                        </div>
                                    </section>
                                    <h3 class="hLh30 txtOf mt10">
                                        <a :href="'/course/'+course.id" :title="course.title" class="course-title fsize18 c-333">{{course.title}}</a>
                                    </h3>
                                    <section class="mt10 hLh20 of">
                    <span class="fr jgTag bg-green" v-if="course.price == 0">
                      <i  class="c-fff fsize12 f-fA">免费</i>
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
                </div>
                <!-- 公共分页 开始 -->
                <div>
                    <div class="paging">
                        <!-- 分页条-->
                        <el-pagination :current-page="currentPage" :page-size="limit" :total="total"
                                       background layout="total, prev, pager, next, jumper" style="padding: 30px 0; text-align: center"
                                       @current-change="getCourseByCondition">
                        </el-pagination>
                    </div>
                </div>
                <!-- 公共分页 结束 -->
            </section>
        </section>
        <!-- /课程列表 结束 -->
    </div>
</template>
<script>
    import courseApi from '@/api/course'
    export default {
        data(){
            return {
                currentPage: 1,
                limit: 4,
                total: 0,
                courseQuery:{
                    "oneSubjectId": "",
                    "twoSubjectId": "",
                    "buyCountSort": false,
                    "gmtCreatSort": true,
                    "priceSort": false,
                    "viewCountSort": false
                },
                courseList: [],
                subjectList: [],
                twoSubjectList: [],
                oneIndex: -1,  // 选择的一级分类下标，为了样式
                twoIndex: -1,   // 选择的二级分类下标，为了样式
                sortNum: 0,    // 选择的排序下标，为了样式
            }
        },
        created() {
            this.getCourseByCondition()
            this.getSubject()
        },
        methods:{
            // 根据条件查询课程通用方法
            getCourseByCondition(page = 1) {
                this.currentPage = page
                courseApi.pageCourseByCondition(this.currentPage, this.limit, this.courseQuery)
                .then(response => {
                    this.total = response.data.data.total
                    this.courseList = response.data.data.records
                })
            },
            // 展示课程分类，数据包括一级分类和二级分类
            getSubject(){
                courseApi.getAllSubject().then(response => {
                    this.subjectList = response.data.data.subjectList
                })
            },
            // 点击一级分类，两个步骤：1.出现二级分类 2.根据一级分类搜索课程。index为下标，添加class用的
            getTwoSubject(subject, index){
                this.oneIndex = index
                this.twoIndex = -1

                this.twoSubjectList = subject.children   // 二级分类
                this.courseQuery.oneSubjectId = subject.id // 搜索条件
                this.courseQuery.twoSubjectId = ""  // 将二级分类id设置为空，防止点完二级再点一级
                this.getCourseByCondition()
            },
            // 点击二级分类，根据二级分类查询课程，index为下标，添加class用的
            queryByTwoSubject(twoSubjectId, index){
                this.twoIndex = index
                this.oneIndex = -1

                this.courseQuery.twoSubjectId = twoSubjectId  // 设置查询条件 二级分类
                this.getCourseByCondition()
            },
            // 点击全部分类按钮，将查询条件中的一级和二级分类id全部值为空，并且index也置为空，同时某个一级分类的二级分类也不展示
            getAllCourse(){
                this.courseQuery.oneSubjectId = ""
                this.courseQuery.twoSubjectId = ""
                this.twoIndex = -1
                this.oneIndex = -1
                this.twoSubjectList = []
                this.getCourseByCondition()
            },
            // 排序条件查询，0-3分别表示 最新（时间）、价格、关注度和购买量的降序排序。sortNum为点击的查询条件，用作class展示
            orderCourse(num){
                this.sortNum = num
                this.courseQuery.gmtCreatSort = false
                this.courseQuery.buyCountSort = false
                this.courseQuery.priceSort = false
                this.courseQuery.viewCountSort = false
                if(num === 0){
                    this.courseQuery.gmtCreatSort = true
                }
                if(num === 1){
                    this.courseQuery.priceSort = true
                }
                if(num === 2){
                    this.courseQuery.viewCountSort = true
                }
                if(num === 3){
                    this.courseQuery.buyCountSort = true
                }
                this.getCourseByCondition()
            }
        }
    };
</script>