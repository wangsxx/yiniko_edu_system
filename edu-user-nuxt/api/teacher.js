import request from '@/utils/request'

export default {
    pageTeacher(currentPage, limit){
        return request({
            url: `/eduservice/edu-teacher/pageTeacher/${currentPage}/${limit}`,
            method: "get"
        })
    },
    teacherDetail(teacherId){
        return request({
            url: `/eduservice/frontTeacher/teacherDetail/${teacherId}`,
            method: "get"
        })
    }
}