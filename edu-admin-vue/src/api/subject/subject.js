import request from '@/utils/request'


export default {
    // 讲师列表+分页
    getSubjectList() {
        return request({
            url: `eduservice/edu-subject/getSubject`,
            method: 'get',
        })
    }
}