<template>
    <div>
        <h2 style="text-align: center;">发布新课程</h2>
        <el-steps :active="3" process-status="wait" align-center style="margin-bottom: 40px;">
            <el-step title="填写课程基本信息"/>
            <el-step title="创建课程大纲"/>
            <el-step title="最终发布"/>
        </el-steps>

        <div class="ccInfo">
            <el-image class="ccImage" :src="coursePublish.cover" :preview-src-list="[coursePublish.cover]"/>
            <div class="main">
                <h2>{{ coursePublish.title }}</h2>
                <p class="gray"><span>共{{ coursePublish.lessonNum }}课时</span></p>
                <p><span>所属分类：{{ coursePublish.oneSubject }} — {{ coursePublish.twoSubject }}</span></p>
                <p>课程讲师：{{ coursePublish.teacherName }}</p>
                <h3 class="red">￥{{ coursePublish.price }}</h3>
            </div>
        </div>

        <el-form label-width="120px" style="width: 90%">
            <el-form-item style="text-align: center">
                <el-button  @click="previous">返回修改</el-button>
                <el-button :disabled="saveBtnDisabled" type="primary" @click="next">发布课程</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
    import course from "@/api/course/course";
    import video from "@/api/video/video";

    export default {
        name: "publish",
        data() {
            return {
                saveBtnDisabled: false,
                courseId: "",
                coursePublish:{
                    title: "",
                    cover: "",
                    price: 0,
                    lessonNum: 0,
                    oneSubject: "",
                    twoSubject: "",
                    teacherName: ""
                }
            };
        },
        created() {
            // 获取路由的id
            if(this.$route.params && this.$route.params.id) {
                this.courseId = this.$route.params.id
                this.getPublish(this.courseId)
            }
        },

        methods: {
            next() {
                // 这里next不执行element组件中的active更新，而是直接跳转到chapter页面
                // 发布课程
                this.$confirm('确定发布课程, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    course.publishCourse(this.courseId).then(response => {
                        this.$message({
                            type: 'success',
                            message: "发布课程成功"
                        })
                        this.$router.push({path: '/course/list'})
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消发布'
                    });
                });

            },
            previous() {
                this.$router.push({path: '/course/chapter/' + this.courseId})
            },
            getPublish(courseId){
                course.getPublishInfo(courseId).then(response => {
                    this.coursePublish = response.data.publish
                })
            }
        }
    }
</script>

<style scoped>
    .ccInfo {
        background: #f5f5f5;
        padding: 20px;
        overflow: hidden;
        border: 1px dashed #DDD;
        margin-bottom: 40px;
        position: relative;
    }
    .ccInfo .ccImage {
        background: #d6d6d6;
        width: 500px;
        height: 278px;
        display: block;
        float: left;
        border: none;
    }
    .ccInfo .main {
        margin-left: 520px;
    }

    .ccInfo .main h2 {
        font-size: 28px;
        margin-bottom: 30px;
        line-height: 1;
        font-weight: normal;
    }
    .ccInfo .main p {
        margin-bottom: 10px;
        word-wrap: break-word;
        line-height: 24px;
        max-height: 48px;
        overflow: hidden;
    }

    .ccInfo .main p {
        margin-bottom: 10px;
        word-wrap: break-word;
        line-height: 24px;
        max-height: 48px;
        overflow: hidden;
    }
    .ccInfo .main h3 {
        left: 540px;
        bottom: 20px;
        line-height: 1;
        font-size: 28px;
        color: #d32f24;
        font-weight: normal;
        position: absolute;
    }
</style>