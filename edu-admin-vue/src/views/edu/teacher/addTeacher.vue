<template>
    <div class="app-container">
        <el-form label-width="120px">
            <el-form-item label="讲师名称">
                <el-input v-model="teacher.name"/>
            </el-form-item>
            <el-form-item label="讲师排序">
                <el-input-number v-model="teacher.sort" controls-position="right" :min="0"/>
            </el-form-item>
            <el-form-item label="讲师头衔">
                <el-select v-model="teacher.level" clearable placeholder="请选择">
                    <!--
                      数据类型一定要和取出的json中的一致，否则没法回填
                      因此，这里value使用动态绑定的值，保证其数据类型是number
                    -->
                    <el-option :value="1" label="高级讲师"/>
                    <el-option :value="2" label="首席讲师"/>
                </el-select>
            </el-form-item>
            <el-form-item label="讲师资历">
                <el-input v-model="teacher.career"/>
            </el-form-item>
            <el-form-item label="讲师简介">
                <el-input v-model="teacher.intro" :rows="10" type="textarea"/>
            </el-form-item>

            <!-- 讲师头像：TODO -->
            <!-- 讲师头像 -->
            <el-form-item label="讲师头像">
                <!-- 头衔缩略图 -->
                <pan-thumb :image="teacher.avatar"/>
                <!-- 文件上传按钮 -->
                <el-button type="primary" icon="el-icon-upload" @click="imagecropperShow=true">更换头像
                </el-button>

                <!--
            v-show：是否显示上传组件
            :key：类似于id，如果一个页面多个图片上传控件，可以做区分
            :url：后台上传的url地址
            @close：关闭上传组件
            @crop-upload-success：上传成功后的回调
              <input type="file" name="file"/>
            -->
                <!-- 这里是调用了一次接口的，使用的应该是form表单， cropSuccess中包含了返回的数据-->
                <image-cropper
                        v-show="imagecropperShow"
                        :width="300"
                        :height="300"
                        :key="imagecropperKey"
                        :url="BASE_API+'/eduoss/fileoss'"
                        field="file"
                        @close="close"
                        @crop-upload-success="cropSuccess"/>
            </el-form-item>

            <el-form-item>
                <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
    import teacherApi from '@/api/teacher/teacher'
    import ImageCropper from '@/components/ImageCropper'
    import PanThumb from '@/components/PanThumb'

    export default {
        name: "addTeacher",
        components:{
            ImageCropper,
            PanThumb,
        },
        data(){
            return {
                teacher :{
                    name: "",
                    sort: 0,
                    level: 1,
                    career: "",
                    intro: "",
                    avatar: ""
                },
                saveBtnDisabled: false, // 保存按钮是否禁用,
                imagecropperKey: 0, // 上传文件的唯一标识
                imagecropperShow: false,
                BASE_API: process.env.BASE_API
            }
        },
        created() {
          if(this.$route.params && this.$route.params.id){
              const id = this.$route.params.id
              this.getTeacherById(id)
          }
        },
        watch:{
          // 路由监听，路由发生变化，该方法执行
          $route(to, from){
              console.log(to,from)
              this.teacher = {
                  name: "",
                  sort: 0,
                  level: 1,
                  career: "",
                  intro: ""
              }
          }
        },
        methods:{
            close(){
                // 关闭上传弹窗的方法
                this.imagecropperShow = false
                // 不改这个key组件不会初始化，也就是保存图像之后再次点按钮不能重新上传，需要关闭在点
                this.imagecropperKey = this.imagecropperKey + 1
            },
            cropSuccess(data){
                // 上传成功的方法
                this.imagecropperShow = false
                this.imagecropperKey = this.imagecropperKey + 1
                this.teacher.avatar = data.url
                console.log(data)
            },
            saveOrUpdate(){
                // 根据teacher里面是否有id来判断
                if(this.teacher.id){
                    this.updateTeacher()
                }else{
                    this.addTeacher()
                }
            },
            addTeacher(){
                teacherApi.addTeacher(this.teacher).then(response =>{
                    this.$message({
                        message: '添加教师成功！',
                        type: 'success'
                    });
                    this.$router.push({ path: '/teacher/teacherList' })
                })
            },
            getTeacherById(id){
                teacherApi.getTeacherById(id).then(response => {
                    this.teacher = response.data.teacher
                })
            },
            updateTeacher(){
                teacherApi.updateTeacher(this.teacher).then(response =>{
                    this.$message({
                        message: '修改教师成功！',
                        type: 'success'
                    });
                    this.$router.push({ path: '/teacher/teacherList' })
                })
            }
        }
    }
</script>

<style scoped>

</style>