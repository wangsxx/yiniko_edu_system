import request from '@/utils/request'

export default {
    pageComment(courseId, currentPage, limit){
        return request({
            url: `/eduservice/edu-comment/getCommentByPage/${courseId}/${currentPage}/${limit}`,
            method: "get"
        })
    },
    submitComment(comment){
        return request({
            url: `/eduservice/edu-comment/addComment`,
            method: "post",
            data: comment
        })
    }
}