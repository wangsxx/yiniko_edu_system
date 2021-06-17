<template>
  <div id="aCoursesList" class="bg-fa of">
    <!-- 讲师列表 开始 -->
    <section class="container">
      <header class="comm-title all-teacher-title">
        <h2 class="fl tac">
          <span class="c-333">全部讲师</span>
        </h2>
        <section class="c-tab-title">
          <a id="subjectAll" title="全部" href="#">全部</a>
        </section>
      </header>
      <section class="c-sort-box unBr">
        <div>
          <!-- /无数据提示 开始-->
          <section v-if="teacherList==null" class="no-data-wrap">
            <em class="icon30 no-data-ico">&nbsp;</em>
            <span class="c-666 fsize14 ml10 vam">没有相关数据，小编正在努力整理中...</span>
          </section>
          <!-- /无数据提示 结束-->
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
        </div>
        <!-- 公共分页 开始 -->
        <div>
          <div class="paging">
              <!-- 分页条-->
              <el-pagination :current-page="page" :page-size="limit" :total="total"
                             background layout="total, prev, pager, next, jumper" style="padding: 30px 0; text-align: center"
                             @current-change="getTeacherList">
              </el-pagination>
          </div>
        </div>
        <!-- 公共分页 结束 -->
      </section>
    </section>
    <!-- /讲师列表 结束 -->
  </div>
</template>
<script>

import teacherApi from "@/api/teacher";
export default {
    data(){
        return {
            page: 1,   // 当前页数
            limit: 8,  // 每页条数
            total: 0, // 总记录数
            teacherList: null  // 查询结果
        }
    },
    created() {
        // 在渲染页面直接调用发
        this.getTeacherList()
    },
    methods: {
        getTeacherList(page = 1) {
            this.page = page
            teacherApi.pageTeacher(this.page, this.limit)
                .then(response => {
                    // 请求成果，返回结果
                    this.teacherList = response.data.data.records
                    this.total = response.data.data.total
                })
                .catch(error => {
                    console.log(error)
                })
        },
    }
};
</script>