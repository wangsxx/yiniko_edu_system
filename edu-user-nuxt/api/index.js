import request from '@/utils/request'

export default {
    getPopularCourse(limit){
        return request({
            url: `/eduservice/front/popularCourse/${limit}`,
            method: "get"
        })
    },
    getPopularTeacher(limit){
        return request({
            url: `/eduservice/front/popularTeacher/${limit}`,
            method: "get"
        })
    }
}