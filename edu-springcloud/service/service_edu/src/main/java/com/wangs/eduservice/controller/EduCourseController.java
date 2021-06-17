package com.wangs.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangs.commonutils.R;
import com.wangs.eduservice.entity.EduCourse;
import com.wangs.eduservice.entity.vo.*;
import com.wangs.eduservice.nacosClient.VideoClient;
import com.wangs.eduservice.service.EduChapterService;
import com.wangs.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
@RequestMapping("/eduservice/edu-course")
public class EduCourseController {

    @Autowired
    private EduCourseService eduCourseService;

    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private VideoClient videoClient;

    // 添加课程基本信息
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        String cid = eduCourseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("cid", cid);
    }

    // 根据课程ID查询课程基本信息
    @GetMapping("getCourseById/{id}")
    public R getCourseById(@PathVariable String id){
        CourseInfoVo courseInfoVo = eduCourseService.getCourseById(id);
        return R.ok().data("course", courseInfoVo);
    }

    // 根据ID修改课程
    @PostMapping("updateCourse")
    public R updateCourse(@RequestBody CourseInfoVo courseInfoVo){
        eduCourseService.updateCourse(courseInfoVo);
        return R.ok();
    }

    @GetMapping("getPublish/{courseId}")
    public R getPublish(@PathVariable  String courseId){
        PublishVo publishVo = eduCourseService.getPublish(courseId);
        return R.ok().data("publish", publishVo);
    }

    // 课程发布，修改status
    @PostMapping("publish/{courseId}")
    public R publish(@PathVariable String courseId){
        EduCourse course = new EduCourse();
        course.setId(courseId);
        course.setStatus("Normal");
        eduCourseService.publish(course);
        return R.ok();
    }

    // 课程列表接口
    // 组合条件查询带分页
    @PostMapping("pageCourseCondition/{current}/{limit}")
    public R pageCourseCondition(@PathVariable("current") long current,
                                 @PathVariable("limit") long limit,
                                 @RequestBody(required = false) CourseQuery courseQuery){
        // 创建page对象
        Page<EduCourse> eduCoursePage = new Page<EduCourse>(current, limit);
        // 创建wrapper
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<EduCourse>();
        // 使用动态sql 组合查询
        String title = courseQuery.getTitle();
        String status = courseQuery.getStatus();

        if (!StringUtils.isEmpty(title)){
            queryWrapper.like("title", title);
        }
        if (!StringUtils.isEmpty(status)){
            queryWrapper.eq("status", status);
        }
        queryWrapper.orderByDesc("gmt_create");
        eduCourseService.page(eduCoursePage, queryWrapper);  // 封装给eduTeacherPage对象

        long total = eduCoursePage.getTotal();
        List<EduCourse> records = eduCoursePage.getRecords();
        return R.ok().data("total", total).data("records", records);
    }

    // 删除课程，将和课程有关的内容全部删除，包括课程表、课程描述表、章节表、小节表
    @DeleteMapping("delCourse/{courseId}")
    public R delCourse(@PathVariable String courseId){
        List<ChapterVo> chapterVoList = chapterService.getChapterVideoByCourseId(courseId);
        List<String> videoIds = new ArrayList<String>();
        for(ChapterVo chapterVo: chapterVoList){
            for(VideoVo videoVo: chapterVo.getChildren()){
                if(!StringUtils.isEmpty(videoVo.getVideoSourceId())){
                    videoIds.add(videoVo.getVideoSourceId());
                }
            }
        }
        videoClient.delAllVideo(videoIds);
        eduCourseService.delCourse(courseId);
        return R.ok();
    }
}

