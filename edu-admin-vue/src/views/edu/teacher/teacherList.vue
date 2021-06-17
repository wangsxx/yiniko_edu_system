<template>
    <div class="app-container">

        <el-form :inline="true"  class="demo-form-inline">
            <el-form-item label="讲师名">
                <el-input v-model="teacherQuery.name" placeholder="讲师名"></el-input>
            </el-form-item>
            <el-form-item>
                <el-select v-model="teacherQuery.level" placeholder="讲师头衔">
                    <el-option label="高级讲师" value="1"></el-option>
                    <el-option label="首席讲师" value="2"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="添加时间">
                <el-date-picker v-model="teacherQuery.begin" type="date"
                                placeholder="选择开始日期" value-format="yyyy-MM-dd HH:mm:ss" default-time="00:00:00"/>
                <el-date-picker v-model="teacherQuery.end" type="date"
                                placeholder="选择结束日期" value-format="yyyy-MM-dd HH:mm:ss" default-time="00:00:00"/>
            </el-form-item>

            <el-form-item>
                <el-button type="primary" icon="el-icon-search" @click="searchTeacherByCondition()">查询</el-button>
                <el-button type="default" icon="el-icon-refresh" @click="reset()">清空</el-button>
            </el-form-item>
        </el-form>



        <el-table :data="teacherList" border style="width: 100%" >
            <el-table-column label="序号" width="100">
                <template slot-scope="scope">
                    {{(page-1)*limit + scope.$index + 1}}
                </template>
            </el-table-column>
            <el-table-column prop="name" label="姓名" width="100"> </el-table-column>
            <el-table-column prop="intro" label="简介" width="300"> </el-table-column>
            <el-table-column label="头衔" width="150">
                <template slot-scope="scope">
                    {{scope.row.level === 1? '高级讲师':'首席讲师'}}
                </template>
            </el-table-column>
            <el-table-column prop="career" label="资历" width="150"> </el-table-column>
            <el-table-column prop="gmtCreate" label="添加时间" width="160"> </el-table-column>
            <el-table-column prop="gmtModified" label="修改时间" width="160"> </el-table-column>
            <el-table-column prop="sort" label="排序" width="60"> </el-table-column>

            <el-table-column label="操作" width="200">
                <template slot-scope="scope">
                    <router-link :to="'/teacher/editTeacher/' + scope.row.id" >
                        <el-button type="primary" icon="el-icon-edit"  size="small">编辑</el-button>
                    </router-link>
                    <el-button type="danger" icon="el-icon-delete" size="small" @click="delTeacherById(scope.row.id)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <!-- 分页条-->
        <el-pagination :current-page="page" :page-size="limit" :total="total"
                       background layout="total, prev, pager, next, jumper" style="padding: 30px 0; text-align: center"
                       @current-change="getTeacherList">
        </el-pagination>
    </div>
</template>

<script>
    import teacher from '@/api/teacher/teacher'

    export default {
        name: "teacherList",
        data(){
            return {
                page: 1,   // 当前页数
                limit: 3,  // 每页条数
                total: 0, // 总记录数
                teacherQuery: {
                    name: null,
                    level: null,
                    begin: null,
                    end: null,
                },  // 组合查询信息
                teacherList: null  // 查询结果
            }
        },
        created() {
            // 在渲染页面直接调用发
            this.getTeacherList()
        },
        methods:{
            getTeacherList(page = 1){
                this.page = page
                teacher.getTeacherList(this.page, this.limit, this.teacherQuery)
                    .then(response => {
                        // 请求成果，返回结果
                        this.teacherList = response.data.records
                        this.total = response.data.total
                        console.log(this.teacherList)
                        console.log(this.total)
                    })
                    .catch(error =>{
                        console.log(error)
                    })
            },
            reset(){
                this.teacherQuery.name = null
                this.teacherQuery.level = null
                this.teacherQuery.begin = null
                this.teacherQuery.end = null
                this.getTeacherList()
            },
            searchTeacherByCondition(){
                this.getTeacherList()
            },
            delTeacherById(id){
                this.$confirm('此操作将删除该教师, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    // 计算当前页，如果当前页的记录删完了返回上一页
                    let page = this.page
                    if(this.teacherList.length === 1 && this.page > 1){
                        page = page -1
                    }
                    // 执行删除记录
                    teacher.delTeacherById(id)
                        .then(response => {
                            this.$message({
                                type: 'success',
                                message: '删除成功!'
                            });
                            // 得到记录列表，如果删完了返回上一页，如果没有则继续显示这一页的
                            this.getTeacherList(page)
                        })
                        .catch(error => {
                        })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });

            }
        }
    }
</script>

<style scoped>

</style>