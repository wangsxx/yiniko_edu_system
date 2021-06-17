<template>
    <div>
        <h2 style="text-align: center;">发布新课程</h2>
        <el-steps :active="1" process-status="wait" align-center style="margin-bottom: 40px;">
            <el-step title="填写课程基本信息"/>
            <el-step title="创建课程大纲"/>
            <el-step title="最终发布"/>
        </el-steps>

        <el-form label-width="120px">
            <el-form-item label="课程标题" style="width: 90%">
                <el-input v-model="courseInfo.title" placeholder=" 示例：机器学习项目课：从基础到搭建项目视频课程。专业名称注意大小写"/>
            </el-form-item>

            <el-form-item label="选择课程分类" style="width: 90%">
                <el-cascader v-model="chooseSubject" :options="subjectList" :show-all-levels="false" placeholder="请选择课程分类"
                            :props="props" @change="subjectChange"></el-cascader>
            </el-form-item>

            <!-- 课程讲师 TODO 根据数据库选择 -->
            <el-form-item label="选择讲师" style="width: 90%">
                <el-select v-model="courseInfo.teacherId" placeholder="请选择任教讲师">
                    <el-option v-for="teacher in teacherList" :key="teacher.id" :label=teacher.name :value="teacher.id"></el-option>
                </el-select>
            </el-form-item>


            <el-form-item label="总课时">
                <el-input-number :min="0" v-model="courseInfo.lessonNum" controls-position="right" placeholder="请填写课程的总课时数"/>
            </el-form-item>

            <!-- 课程简介 TODO -->
            <el-form-item label="课程简介" style="width: 90%">
                <el-input v-model="courseInfo.description"/>
            </el-form-item>

            <!-- 课程封面 TODO -->
            <el-form-item label="课程封面" style="width: 90%">
                <el-upload
                        :show-file-list="false"
                        :on-success="handleAvatarSuccess"
                        :before-upload="beforeAvatarUpload"
                        :action="BASE_API+'/eduoss/fileoss'"
                        class="avatar-uploader">

                    <!--  TODO 这里不显示的原因  版本原因 升级element-ui即可-->
                    <el-image v-if="courseInfo.cover" style="width: 150px; height: 150px" :src="courseInfo.cover" :preview-src-list="[courseInfo.cover]"/>
                    <i v-else class="el-icon-plus avatar-uploader-icon"></i>

                </el-upload>
            </el-form-item>

            <el-form-item label="课程价格">
                <el-input-number :min="0" v-model="courseInfo.price" controls-position="right" placeholder="免费课程请设置为0元"/> 元
            </el-form-item>


            <el-form-item style="text-align: center">
                <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存并下一步</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
    import course from "@/api/course/course"
    import teacher from "@/api/teacher/teacher";
    import subject from "@/api/subject/subject";

    export default {
        name: "info",
        data() {
            return {
                saveBtnDisabled: false,
                courseInfo: {
                    teacherId: "",
                    subjectId: "",
                    title: "",
                    lessonNum: "",
                    price: "",
                    description: "",
                    cover: "",
                    subjectParentId: ""
                },
                teacherList: [],  // 所有的讲师信息分类
                subjectList: [],  // 所有课程分类信息
                chooseSubject: [],  // 这里是级联下拉列表选择的元素，是个数组，保存的是选择的多级的value
                // 用于更换el-cascader所需要的json中的key
                props:{
                    value: "id",
                    label: "title"
                },
                BASE_API: process.env.BASE_API,
                courseId: ""
            };
        },
        created() {
            if(this.$route.params && this.$route.params.id) {
                this.courseId = this.$route.params.id;
                this.getInfoById()
            }
            console.log(this.chooseSubject)
            this.getAllTeacher()
            this.getAllSubject()
        },
        watch:{
            // 路由监听，路由发生变化，该方法执行
            $route(to, from){
                this.courseInfo= {
                    teacherId: "",
                    subjectId: "",
                    title: "",
                    lessonNum: "",
                    price: "",
                    description: "",
                    cover: "",
                    subjectParentId: ""
                }
                this.chooseSubject= []  // 这里是级联下拉列表选择的元素，是个数组，保存的是选择的多级的value
                // 用于更换el-cascader所需要的json中的key
                this.courseId= ""
            }
        },
        methods: {
            getInfoById(){
              course.getCourseInfoById(this.courseId).then(response => {
                  this.courseInfo = response.data.course;
                  // 级联下拉列表
                  this.chooseSubject[0] = this.courseInfo.subjectParentId
                  this.chooseSubject[1] = this.courseInfo.subjectId
              })
            },
            saveOrUpdate() {
                if(!this.courseInfo.id){
                    // 没有id，则是添加
                    this.addCourse()
                }else{
                    // 存在id，则是修改
                    this.updateCourse()
                }

            },
            updateCourse(){
                course.updateCourseInfo(this.courseInfo)
                    .then(response => {
                        // 提示
                        this.$message({
                            type: 'success',
                            message: "修改课程信息成功"
                        })
                        // 这里next不执行element组件中的active更新，而是直接跳转到chapter页面
                        this.$router.push({path:'/course/chapter/'+this.courseInfo.id})
                    })
            },
            addCourse(){
                course.addCourseInfo(this.courseInfo)
                    .then(response => {
                        // 提示
                        this.$message({
                            type: 'success',
                            message: "添加课程信息成功"
                        })
                        // 这里next不执行element组件中的active更新，而是直接跳转到chapter页面
                        this.$router.push({path:'/course/chapter/'+response.data.cid})
                    })
            },
            getAllTeacher(){
                teacher.getAllTeacher()
                    .then( response => {
                        this.teacherList = response.data.items
                    })
            },
            getAllSubject(){
                subject.getSubjectList().then(response => {
                    this.subjectList = response.data.subjectList
                })
            },
            // 这个当选择的subject改变后，更换id
            subjectChange(){
                this.courseInfo.subjectParentId = this.chooseSubject[0]
                this.courseInfo.subjectId = this.chooseSubject[1]
                console.log(this.courseInfo.subjectParentId)
                console.log(this.courseInfo.subjectId)
            },
            handleAvatarSuccess(res, file) {
                // 课程图片上传成功的回调方法
                this.courseInfo.cover = res.data.url
            },
            beforeAvatarUpload(file){
                // 上传之前的方法，规定文件类型和文件大小
                const isJPG = file.type === 'image/jpeg'
                const isLt2M = file.size / 1024 / 1024 < 2
                if (!isJPG) {
                    this.$message.error('上传头像图片只能是 JPG 格式!')
                }
                if (!isLt2M) {
                    this.$message.error('上传头像图片大小不能超过 2MB!')
                }
                return isJPG && isLt2M
            }
        }
    }
</script>

<style>
    .avatar-uploader .el-upload {
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
    }
    .avatar-uploader .el-upload:hover {
        border-color: #409EFF;
    }
    .avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 150px;
        height: 150px;
        line-height: 150px;
        text-align: center;
    }
    .avatar {
        width: 150px;
        height: 150px;
        display: block;
    }
</style>