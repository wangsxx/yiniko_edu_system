package com.wangs.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangs.commonutils.R;
import com.wangs.eduservice.entity.EduCourse;
import com.wangs.eduservice.entity.EduCourseDescription;
import com.wangs.eduservice.entity.EduTeacher;
import com.wangs.eduservice.entity.vo.ChapterVo;
import com.wangs.eduservice.entity.vo.CourseInfoVo;
import com.wangs.eduservice.entity.vo.FrontCourseQuery;
import com.wangs.eduservice.feignClient.orderClient;
import com.wangs.eduservice.service.EduChapterService;
import com.wangs.eduservice.service.EduCourseDescriptionService;
import com.wangs.eduservice.service.EduCourseService;
import com.wangs.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

/**
 * @File : FrontCourseController.java
 * @Time : 2021/5/27 16:10
 * @Author: Wangs
 * @Decs :
 */
@RestController
@RequestMapping("/eduservice/frontCourse")
public class FrontCourseController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private EduCourseDescriptionService courseDescriptionService;

    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private orderClient orderClient;

    // RequestBody 必须为post请求
    // required = false 请求条件可以围攻
    @PostMapping("pageCourseByCondition/{current}/{limit}")
    public R pageCourseByCondition(@PathVariable("current") long current,
                                   @PathVariable("limit") long limit,
                                   @RequestBody(required = false) FrontCourseQuery courseQuery){
        // 创建page对象
        Page<EduCourse> pageCourse = new Page<EduCourse>(current, limit);
        HashMap<String, Object> records = courseService.getFrontCourseByCondition(pageCourse, courseQuery);

        return R.ok().data("total", records.get("total")).data("records", records.get("records"));
    }

    @GetMapping("courseDetail/{id}")
    public R courseDetail(@PathVariable("id") String id){
        // 需要的内容
        // 1. 课程信息 图片、购买数、课时数、浏览数、名称、价格
        EduCourse course = courseService.getById(id);
        // 2. 讲师信息 名称、图像等
        EduTeacher teacher = teacherService.getById(course.getTeacherId());
        // 3. 课程简介
        EduCourseDescription introduction = courseDescriptionService.getById(id);
        // 4. 课程大纲 包括章节、小节和视频地址
        List<ChapterVo> chapterVideo = chapterService.getChapterVideoByCourseId(id);

        return R.ok().data("courseInfo", course).data("teacherInfo", teacher).
                data("courseIntro", introduction).data("chapterVideo", chapterVideo);
    }
}
