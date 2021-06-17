package com.wangs.eduservice.controller;


import com.wangs.commonutils.R;
import com.wangs.eduservice.entity.EduChapter;
import com.wangs.eduservice.entity.vo.ChapterVo;
import com.wangs.eduservice.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author wangs
 * @since 2021-03-09
 */
@RestController
@RequestMapping("/eduservice/edu-chapter")
public class EduChapterController {

    @Autowired
    private EduChapterService chapterService;

    // 返回课程大纲列表，根据课程ID查询
    @GetMapping("getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable String courseId){
        List<ChapterVo> list = chapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data("allChapterVideo", list);
    }

    @PostMapping("addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter){
        chapterService.addChapter(eduChapter);
        return R.ok();
    }

    @GetMapping("getChapter/{chapterId}")
    public R getChapter(@PathVariable String chapterId){
        EduChapter chapter = chapterService.getChapterById(chapterId);
        return R.ok().data("chapter", chapter);
    }

    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter){
        chapterService.updateChapter(eduChapter);
        return R.ok();
    }

    @DeleteMapping("delChapter/{chapterId}")
    public R delChapter(@PathVariable String chapterId){
        chapterService.delChapter(chapterId);
        return R.ok();
    }
}

