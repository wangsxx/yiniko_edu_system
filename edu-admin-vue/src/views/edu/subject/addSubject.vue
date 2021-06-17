<template>
    <div>
        <el-form label-width="120px">
            <el-form-item label="信息描述">
                <el-tag type="info">excel模版说明</el-tag>
                <el-tag>
                    <i class="el-icon-download"/>
                    <a :href="'/static/01.xlsx'">点击下载模版</a>
                </el-tag>
            </el-form-item>

            <el-form-item label="选择Excel">
                <el-upload
                        ref="upload"
                        :auto-upload="false"
                        :on-success="fileUploadSuccess"
                        :on-error="fileUploadError"
                        :on-preview="handlePreview"
                        :on-remove="handleRemove"
                        :disabled="importBtnDisabled"
                        :limit="1"
                        :action="BASE_API+'/eduservice/edu-subject/addSubject'"
                        name="file"
                        accept="application/vnd.ms-excel">
                    <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
                    <el-button
                            :loading="loading"
                            style="margin-left: 10px;"
                            size="small"
                            type="success"
                            @click="submitUpload">上传到服务器</el-button>
                </el-upload>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
    export default {
        name: "addSubject",
        data() {
            return {
                BASE_API: process.env.BASE_API,
                importBtnDisabled: false,
                loading: false
            }
        },
        created() {

        },
        methods: {
            // 点击上传文件按钮
            submitUpload() {
                this.importBtnDisabled = true
                this.loading = true
                this.$refs.upload.submit()
            },
            //上传成功
            fileUploadSuccess(response) {
                //提示信息
                this.loading = false
                this.$message({
                    type: 'success',
                    message: '添加课程分类成功'
                })
                //跳转课程分类列表
                //路由跳转
                this.$router.push({path:'/subject/subjectList'})
            },
            //上传失败
            fileUploadError() {
                this.loading = false
                this.$message({
                    type: 'error',
                    message: '添加课程分类失败'
                })
            },
            handleRemove(file, fileList) {
                // 移除上传文件方法
                console.log(file, fileList);
            },
            handlePreview(file) {
                // 点击上次文件方法
                console.log(file);
            }
        }
    }
</script>

<style scoped>

</style>