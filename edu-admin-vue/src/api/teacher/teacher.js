import request from '@/utils/request'


export default {
  // 讲师列表+分页
  getTeacherList(current, limit, teacherQuery) {
    return request({
      url: `/eduservice/edu-teacher/pageTeacherCondition/${current}/${limit}`,
      method: 'post',
      data: teacherQuery
    })
  },
    delTeacherById(id){
        return request({
            url: `/eduservice/edu-teacher/delTeacher/${id}`,
            method: 'delete'
        })
    },
    addTeacher(teacher){
        return request({
            url: `/eduservice/edu-teacher/addTeacher`,
            method: 'post',
            data: teacher
        })
    },
    getTeacherById(id){
        return request({
            url: `/eduservice/edu-teacher/getTeacher/${id}`,
            method: 'get'
        })
    },
    updateTeacher(teacher){
        return request({
            url: `/eduservice/edu-teacher/updateTeacher`,
            method: 'post',
            data: teacher
        })
    },
    getAllTeacher(){
        return request({
            url: `/eduservice/edu-teacher/getAll`,
            method: 'get'
        })
    }
}
