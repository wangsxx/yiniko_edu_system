<template>
  <div id="aCoursesList" class="bg-fa of">
    <!-- 讲师介绍 开始 -->
    <section class="container">
      <header class="comm-title">
        <h2 class="fl tac">
          <span class="c-333">讲师介绍</span>
        </h2>
      </header>
      <div class="t-infor-wrap">
        <!-- 讲师基本信息 -->
        <section class="fl t-infor-box c-desc-content">
          <div class="mt20 ml20">
            <section class="t-infor-pic">
              <img :src="teacherInfo.avatar">
            </section>
            <h3 class="hLh30">
              <span class="fsize24 c-333">{{teacherInfo.name}}&nbsp;&nbsp;{{teacherInfo.level==1? "高级讲师":"首席讲师"}}</span>
            </h3>
            <section class="mt10">
              <span class="t-tag-bg">{{teacherInfo.career}}</span>
            </section>
            <section class="t-infor-txt">
              <p
                class="mt20"
              >{{teacherInfo.intro}}</p>
            </section>
            <div class="clear"></div>
          </div>
        </section>
        <div class="clear"></div>
      </div>
      <section class="mt30">
        <div>
          <header class="comm-title all-teacher-title c-course-content">
            <h2 class="fl tac">
              <span class="c-333">主讲课程</span>
            </h2>
            <section class="c-tab-title">
              <a href="javascript: void(0)">&nbsp;</a>
            </section>
          </header>
          <!-- /无数据提示 开始-->
          <section v-if="teacherCourse.length==0" class="no-data-wrap">
            <em class="icon30 no-data-ico">&nbsp;</em>
            <span class="c-666 fsize14 ml10 vam">没有相关数据，小编正在努力整理中...</span>
          </section>
          <!-- /无数据提示 结束-->
          <article v-else class="comm-course-list">
            <ul class="of">
              <li v-for="course in teacherCourse" :key="course.id">
                <div class="cc-l-wrap">
                  <section class="course-img" style="width: 268px;height: 150px">
                    <img :src="course.cover" class="img-responsive" >
                    <div class="cc-mask">
                      <a :href="'/course/'+course.id" title="开始学习" class="comm-btn c-btn-1">开始学习</a>
                    </div>
                  </section>
                  <h3 class="hLh30 txtOf mt10">
                    <a :href="'/course/'+course.id" :title="course.title" class="course-title fsize18 c-333">{{course.title}}</a>
                  </h3>
                </div>
              </li>
            </ul>
            <div class="clear"></div>
          </article>
        </div>
      </section>
    </section>
    <!-- /讲师介绍 结束 -->
  </div>
</template>
<script>
import teacherApi from "@/api/teacher";

export default {
    data(){
        return {
            teacherInfo: {
                id: "",
                name: "",
                intro: "",
                career: "",
                avatar: "",
                level: ""
            },
            teacherCourse: [

            ]
        }
    },
    created() {
        var teacherId = this.$route.params.id
        this.getTeacherDetail(teacherId)
    },
    methods:{
        getTeacherDetail(teacherId){
            teacherApi.teacherDetail(teacherId).then(response =>{
                this.teacherInfo = response.data.data.teacherInfo
                console.log(this.teacherInfo)
                this.teacherCourse = response.data.data.teacherCourse
                console.log(this.teacherCourse)
            })
        }
    }

};
</script>