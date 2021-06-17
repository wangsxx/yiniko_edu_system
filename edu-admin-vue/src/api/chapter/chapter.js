import request from '@/utils/request'


export default {
    // 查询章节小节信息
    getAllChapterVideo(courseId) {
        return request({
            url: `eduservice/edu-chapter/getChapterVideo/${courseId}`,
            method: 'get'
        })
    },
    // 添加章节
    addChapter(ChapterInfo){
        return request({
            url: `eduservice/edu-chapter/addChapter`,
            method: 'post',
            data: ChapterInfo
        })
    },

    // 修改章节
    updateChapter(ChapterInfo){
        return request({
            url: `eduservice/edu-chapter/updateChapter`,
            method: 'post',
            data: ChapterInfo
        })
    },

    // 查询章节
    getChapterInfo(chapterId){
        return request({
            url: `eduservice/edu-chapter/getChapter/${chapterId}`,
            method: 'get',
        })
    },

    // 删除章节
    delChapter(chapterId){
        return request({
            url: `eduservice/edu-chapter/delChapter/${chapterId}`,
            method: 'delete',
        })
    },
}