import request from '@/utils/request'


export default {
    // 添加课程信息
    addCourseInfo(courseInfo) {
        return request({
            url: `eduservice/edu-course/addCourseInfo`,
            method: 'post',
            data: courseInfo
        })
    },

    // 根据ID查找课程信息
    getCourseInfoById(courseId) {
        return request({
            url: `eduservice/edu-course/getCourseById/${courseId}`,
            method: 'get',
        })
    },

    // 修改课程信息
    updateCourseInfo(courseInfo) {
        return request({
            url: `eduservice/edu-course/updateCourse`,
            method: 'post',
            data: courseInfo
        })
    },

    // 发布信息曲儿
    getPublishInfo(courseId) {
        return request({
            url: `eduservice/edu-course/getPublish/${courseId}`,
            method: 'get'
        })
    },

    // 课程发布
    publishCourse(courseId) {
        return request({
            url: `eduservice/edu-course/publish/${courseId}`,
            method: 'post'
        })
    },

    // 课程条件查询
    getCourseByCondition(current, limit, courseQuery){
        return request({
            url: `eduservice/edu-course/pageCourseCondition/${current}/${limit}`,
            method: 'post',
            data: courseQuery
        })
    },

    // 课程全部删除
    delCourseById(courseId) {
        return request({
            url: `eduservice/edu-course/delCourse/${courseId}`,
            method: 'delete'
        })
    }
}