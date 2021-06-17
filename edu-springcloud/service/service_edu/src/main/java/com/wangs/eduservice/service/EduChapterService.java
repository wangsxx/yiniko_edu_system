package com.wangs.eduservice.service;

import com.wangs.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wangs.eduservice.entity.vo.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author wangs
 * @since 2021-03-09
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    void addChapter(EduChapter eduChapter);

    EduChapter getChapterById(String chapterId);

    void updateChapter(EduChapter eduChapter);

    void delChapter(String chapterId);
}
