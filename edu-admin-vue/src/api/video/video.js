import request from '@/utils/request'


export default {
    // 添加章节
    addVideo(video){
        return request({
            url: `eduservice/edu-video/addVideo`,
            method: 'post',
            data: video
        })
    },

    // 修改章节
    updateVideo(video){
        return request({
            url: `eduservice/edu-video/updateVideo`,
            method: 'post',
            data: video
        })
    },

    // 查询章节
    getVideo(videoID){
        return request({
            url: `eduservice/edu-video/getVideoById/${videoID}`,
            method: 'get',
        })
    },

    // 删除章节
    delVideo(videoId){
        return request({
            url: `eduservice/edu-video/delVideo/${videoId}`,
            method: 'delete',
        })
    },

    // 删除阿里云中的视频
    delAlyVideo(AlyVideoId){
        return request({
            url: `eduvideo/video/delVideo/${AlyVideoId}`,
            method: 'delete',
        })
    }
}