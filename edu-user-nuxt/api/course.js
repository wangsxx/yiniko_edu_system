import request from '@/utils/request'

export default {
    pageCourseByCondition(currentPage, limit, courseQuery){
        return request({
            url: `/eduservice/frontCourse/pageCourseByCondition/${currentPage}/${limit}`,
            method: "post",
            data: courseQuery
        })
    },
    getAllSubject(){
        return request({
            url: `/eduservice/edu-subject/getSubject`,
            method: "get",
        })
    },
    courseDetail(id){
        return request({
            url: `/eduservice/frontCourse/courseDetail/${id}`,
            method: "get",
        })
    },
    getVideoPlayAuth(videoId){
        return request({
            url: `/eduvideo/video/getPlayAuth/${videoId}`,
            method: "get",
        })
    }
}