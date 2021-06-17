<template>
    <div class="app-container">
        <el-form :inline="true"  class="demo-form-inline">
            <el-form-item label="课程名">
                <el-input v-model="courseQuery.title" placeholder="课程名"></el-input>
            </el-form-item>
            <el-form-item>
                <el-select v-model="courseQuery.status" placeholder="状态">
                    <el-option label="已发布" value="Normal"></el-option>
                    <el-option label="未发布" value="Draft"></el-option>
                </el-select>
            </el-form-item>

            <el-form-item>
                <el-button type="primary" icon="el-icon-search" @click="searchCourseByCondition()">查询</el-button>
                <el-button type="default" icon="el-icon-refresh" @click="reset()">清空</el-button>
            </el-form-item>
        </el-form>



        <el-table :data="courseList" border style="width: 100%" >
            <el-table-column label="序号" width="100">
                <template slot-scope="scope">
                    {{(page-1)*limit + scope.$index + 1}}
                </template>
            </el-table-column>
            <el-table-column prop="title" label="课程名" width="200"> </el-table-column>
            <el-table-column label="状态" width="150">
                <template slot-scope="scope">
                    {{scope.row.status === "Draft"? '未发布':'已发布'}}
                </template>
            </el-table-column>
            <el-table-column prop="price" label="价格/元" width="150"></el-table-column>
            <el-table-column prop="lessonNum" label="课时数" width="150"></el-table-column>
            <el-table-column prop="buyCount" label="购买数" width="150"></el-table-column>
            <el-table-column prop="viewCount" label="浏览数量" width="150"></el-table-column>
            <el-table-column prop="gmtCreate" label="添加时间" width="160"> </el-table-column>
            <el-table-column label="操作" width="350">
                <template slot-scope="scope">
                    <el-button type="primary" icon="el-icon-edit" size="small" @click="editCourseInfo(scope.row.id)">编辑基本信息</el-button>
                    <el-button type="primary" icon="el-icon-edit" size="small" @click="editCourseChapter(scope.row.id)">编辑大纲</el-button>
                    <el-button type="danger" icon="el-icon-delete" size="small" @click="delCourseById(scope.row.id)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <!-- 分页条-->
        <el-pagination :current-page="page" :page-size="limit" :total="total"
                       background layout="total, prev, pager, next, jumper" style="padding: 30px 0; text-align: center"
                       @current-change="getCourseList">
        </el-pagination>
    </div>
</template>

<script>
    import course from "@/api/course/course";
    import video from "@/api/video/video";
    export default {
        name: "courseList",
        data(){
            return{
                courseQuery:{
                    status: "",
                    title: ""
                },
                courseList: [],
                page: 1,   // 当前页数
                limit: 10,  // 每页条数
                total: 0, // 总记录数
            }
        },
        created() {
            this.getCourseList()
        },
        methods:{
            searchCourseByCondition(){
                this.getCourseList()
            },
            reset(){
                this.courseQuery={
                    status: "",
                    title: ""
                }
                this.getCourseList()
            },
            delCourseById(courseId){
                // 根据ID删除课程
                this.$confirm('此操作将删除该课程, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    course.delCourseById(courseId).then(response => {
                        // 提示信息
                        this.$message({
                            type: 'success',
                            message: "删除课程成功！"
                        })
                        // 刷新页面
                        this.getCourseList(this.page)
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });

            },
            getCourseList(page=1){
                this.page = page
                course.getCourseByCondition(this.page, this.limit, this.courseQuery).then(response => {
                    console.log(response.data.records)
                    this.total = response.data.total
                    this.courseList = response.data.records
                })
            },
            editCourseInfo(id){
                this.$router.push({path:'/course/info/'+ id})
            },
            editCourseChapter(id){
                this.$router.push({path:'/course/chapter/'+ id})
            },
        }
    }
</script>

<style scoped>

</style>