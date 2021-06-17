<template>
    <div>
        <h2 style="text-align: center;">发布新课程</h2>
        <el-steps :active="2" process-status="wait" align-center style="margin-bottom: 40px;">
            <el-step title="填写课程基本信息"/>
            <el-step title="创建课程大纲"/>
            <el-step title="最终发布"/>
        </el-steps>
        <!--  弹窗按钮  -->
        <el-button type="text" @click="openChapterDialog()" style="margin-left: 30px">添加章节</el-button>
        <!-- 添加和修改章节表单 -->
        <el-dialog :visible.sync="dialogChapterFormVisible" title="添加章节">
            <el-form :model="chapter" label-width="120px">
                <el-form-item label="章节标题">
                    <el-input v-model="chapter.title"/>
                </el-form-item>
                <el-form-item label="章节排序">
                    <el-input-number v-model="chapter.sort" :min="0" controls-position="right"/>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogChapterFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveOrUpdate">确 定</el-button>
            </div>
        </el-dialog>

        <!-- 章节 -->
        <ul class="chanpterList">
            <li v-for="chapter in chapterVideoList" :key="chapter.id">
                <p>
                    {{ chapter.title }}
                    <span class="acts">
                        <el-button style="" type="text" @click="openVideo(chapter.id)">添加小节</el-button>
                        <el-button style="" type="text" @click="openEditChatper(chapter.id)">编辑</el-button>
                        <el-button type="text" @click="removeChapter(chapter.id)">删除</el-button>
                    </span>
                </p>

                <!-- 视频 -->
                <ul class="chanpterList videoList">
                    <li  v-for="video in chapter.children" :key="video.id">
                        <p>{{ video.title }}
                            <span class="acts">
                                <el-button type="text" @click="openEditVideo(video.id)">编辑</el-button>
                                <el-button type="text" @click="removeVideo(video.id)">删除</el-button>
                            </span>
                        </p>
                    </li>
                </ul>
            </li>
        </ul>


        <!-- 添加和修改小节表单弹窗 -->
        <el-dialog :visible.sync="dialogVideoFormVisible" title="添加课时">
            <el-form :model="video" label-width="120px">
                <el-form-item label="课时标题">
                    <el-input v-model="video.title"/>
                </el-form-item>
                <el-form-item label="课时排序">
                    <el-input-number v-model="video.sort" :min="0" controls-position="right"/>
                </el-form-item>
                <el-form-item label="是否免费">
                    <el-radio-group v-model="video.isFree">
                        <el-radio :label="true">免费</el-radio>
                        <el-radio :label="false">默认</el-radio>
                    </el-radio-group>
                </el-form-item>
<!--                上传视频-->
                <el-form-item label="上传视频">
                    <el-upload
                            :on-success="handleVodUploadSuccess"
                            :on-remove="handleVodRemove"
                            :before-remove="beforeVodRemove"
                            :on-exceed="handleUploadExceed"
                            :file-list="videoFileList"
                            :action="BASE_API+'/eduvideo/video/uploadVideo'"
                            :limit="1"
                            class="upload-demo">
                        <el-button size="small" type="primary">上传视频</el-button>
                        <el-tooltip placement="right-end">
                            <div slot="content">最大支持1G，<br>
                                支持3GP、ASF、AVI、DAT、DV、FLV、F4V、<br>
                                GIF、M2T、M4V、MJ2、MJPEG、MKV、MOV、MP4、<br>
                                MPE、MPG、MPEG、MTS、OGG、QT、RM、RMVB、<br>
                                SWF、TS、VOB、WMV、WEBM 等视频格式上传</div>
                            <i class="el-icon-question"/>
                        </el-tooltip>
                    </el-upload>
                </el-form-item>


            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogVideoFormVisible = false">取 消</el-button>
                <el-button :disabled="saveVideoBtnDisabled" type="primary" @click="saveOrUpdateVideo">确 定</el-button>
            </div>
        </el-dialog>

        <el-form label-width="120px" style="width: 90%">
            <el-form-item style="text-align: center">
                <el-button  @click="previous">上一步</el-button>
                <el-button :disabled="saveBtnDisabled" type="primary" @click="next">下一步</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
    import chapter from "@/api/chapter/chapter";
    import video from "@/api/video/video";

    export default {
        name: "chapter",
        data(){
            return{
                saveBtnDisabled:false,
                chapterVideoList: [],  // 小节信息
                dialogChapterFormVisible: false,
                dialogVideoFormVisible: false,
                saveVideoBtnDisabled: false,
                courseId: "",
                chapter:{
                    title: "",
                    sort: "",
                    courseId: "",
                    id: "",
                },
                video: {
                    title: "",
                    sort: 0,
                    isFree: 0,
                    videoSourceId: "",
                    courseId: "",
                    chapterId: "",
                    videoOriginalName: ""
                },
                videoFileList: [],  // 这里存放视频信息
                BASE_API: process.env.BASE_API
            }
        },
        created() {
            // 获取路由的id
            if(this.$route.params && this.$route.params.id) {
                this.courseId = this.$route.params.id
                this.getChapterVideo(this.courseId)
            }
        },

        methods: {
            next() {
                // 这里next不执行element组件中的active更新，而是直接跳转到chapter页面
                this.$router.push({path:'/course/publish/'+this.courseId})
            },

            previous(){
                this.$router.push({path:'/course/info/'+this.courseId})
            },
            // ============== 显示章节信息 ============================
            getChapterVideo(id){
                chapter.getAllChapterVideo(id).then(response => {
                    this.chapterVideoList = response.data.allChapterVideo
                })
            },
            /// ================ 视频操作 ===============================
            handleVodUploadSuccess(response, file, fileList){
                // 成功上传，保存视频的id和name，然后点击确定后保存至数据库
                this.video.videoSourceId = response.data.videoId
                this.video.videoOriginalName = file.name
            },
            handleVodRemove(file, fileList) {
                // 这里移除应该删除记录（清空id和name），并且删除阿里云里存储的视频
                // 判断是否存在id
                if(this.video.videoSourceId){
                    video.delAlyVideo(this.video.videoSourceId).then(response => {
                        this.$message({
                            type: 'success',
                            message: '删除成功!'
                        });
                        this.videoFileList = []
                    })
                }
                this.video.videoSourceId = ""
                this.video.videoOriginalName = ""
            },
            handleUploadExceed(files, fileList) {
                this.$message.warning(`当前限制选择 1 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
            },
            beforeVodRemove(file, fileList) {
                return this.$confirm(`确定移除 ${ file.name }？`);
            },


            // ================= 小节信息操作 ==============================
            openVideo(chapterId){
                // 表单数据清空
                this.video= {
                    title: "",
                    sort: 0,
                    isFree: 0,
                    videoSourceId: "",
                    courseId: "",
                    chapterId: "",
                    videoOriginalName: ""
                }
                this.dialogVideoFormVisible = true
                this.videoFileList = []
                this.video.chapterId = chapterId
            },
            saveOrUpdateVideo(){
                if(!this.video.id){
                    this.video.courseId = this.courseId
                    // 不存在ID, 添加章节
                    video.addVideo(this.video).then(response => {
                        // 关闭弹框
                        this.dialogVideoFormVisible = false
                        // 提示信息
                        this.$message({
                            type: 'success',
                            message: "添加小节成功！"
                        })
                        this.videoFileList = []
                        // 刷新页面
                        this.getChapterVideo(this.courseId)
                    })
                }else{
                    // 如果存在video.id 说明是修改页面
                    video.updateVideo(this.video).then(response => {
                        // 关闭弹框
                        this.dialogVideoFormVisible = false
                        // 提示信息
                        this.$message({
                            type: 'success',
                            message: "修改小节成功！"
                        })
                        this.videoFileList = []
                        // 刷新页面
                        this.getChapterVideo(this.courseId)
                    })
                }
            },
            openEditVideo(videoId){

              video.getVideo(videoId).then(response => {
                  this.video = response.data.video
                  if(this.video.videoSourceId){
                      this.videoFileList = [{name: this.video.videoOriginalName}]
                  }
                  this.dialogVideoFormVisible = true
                  console.log(this.video)
              })
            },
            removeVideo(id){
                this.$confirm('此操作将删除该小节, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    video.delVideo(id).then(response => {
                        // 提示信息
                        this.$message({
                            type: 'success',
                            message: "删除章节成功！"
                        })
                        // 刷新页面
                        this.getChapterVideo(this.courseId)
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },


            // ================== 章节信息 ============================
            //弹出添加章节页面
            openChapterDialog() {
                //弹框
                this.dialogChapterFormVisible = true
                //表单数据清空
                this.chapter.title = ''
                this.chapter.sort = 0
                this.chapter.id = ""
            },
            // 保存或更新章节信息
            saveOrUpdate(){
                if(!this.chapter.id){
                    this.chapter.courseId = this.courseId
                    // 不存在ID, 添加章节
                    chapter.addChapter(this.chapter).then(response => {
                        // 关闭弹框
                        this.dialogChapterFormVisible = false
                        // 提示信息
                        this.$message({
                            type: 'success',
                            message: "添加章节成功！"
                        })
                        // 刷新页面
                        this.getChapterVideo(this.courseId)
                    })
                }else{
                    // 存在ID 修改
                    chapter.updateChapter(this.chapter).then(response => {
                        // 关闭弹框
                        this.dialogChapterFormVisible = false
                        // 提示信息
                        this.$message({
                            type: 'success',
                            message: "修改章节成功！"
                        })
                        // 刷新页面
                        this.getChapterVideo(this.courseId)
                    })
                }
            },
            openEditChatper(id){
                chapter.getChapterInfo(id).then(response => {
                    this.chapter = response.data.chapter
                    this.dialogChapterFormVisible = true
                })
            },
            removeChapter(id){
                this.$confirm('此操作将删除该章节, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    chapter.delChapter(id).then(response => {
                        // 提示信息
                        this.$message({
                            type: 'success',
                            message: "删除章节成功！"
                        })
                        // 刷新页面
                        this.getChapterVideo(this.courseId)
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
    .chanpterList{
        position: relative;
        list-style: none;
        margin: 0;
        padding: 0;
    }
    .chanpterList li{
        position: relative;
    }
    .chanpterList p{
        float: left;
        font-size: 20px;
        margin: 10px 0;
        padding: 10px;
        height: 70px;
        line-height: 50px;
        width: 100%;
        border: 1px solid #DDD;
    }
    .chanpterList .acts {
        float: right;
        font-size: 14px;
    }

    .videoList{
        padding-left: 50px;
    }
    .videoList p{
        float: left;
        font-size: 14px;
        margin: 10px 0;
        padding: 10px;
        height: 50px;
        line-height: 30px;
        width: 100%;
        border: 1px dotted #DDD;
    }
</style>