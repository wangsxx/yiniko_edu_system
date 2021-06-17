package com.wangs.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wangs.commonutils.R;
import com.wangs.eduservice.entity.EduCourse;
import com.wangs.eduservice.entity.EduTeacher;
import com.wangs.eduservice.service.EduCourseService;
import com.wangs.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @File : CourseFrontController.java
 * @Time : 2021/3/24 10:31
 * @Author: Wangs
 * @Decs :
 */
@RestController
@RequestMapping("/eduservice/front")
public class EduFrontController {
    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduTeacherService teacherService;


    @GetMapping("popularCourse/{limit}")
    @Cacheable(value = "index", key = "'popularCourse'")
    public R popularCourse(@PathVariable int limit){
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<EduCourse>();
        wrapper.eq("status", "Normal");
        wrapper.orderByDesc("view_count");
        wrapper.last("limit "+limit);
        List<EduCourse> courses = courseService.list(wrapper);
        return R.ok().data("courseList", courses);
    }

    @GetMapping("popularTeacher/{limit}")
    @Cacheable(value = "index", key = "'popularTeacher'")
    public R popularTeacher(@PathVariable int limit){
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<EduTeacher>();
        wrapper.orderByDesc("id");
        wrapper.last("limit "+limit);
        List<EduTeacher> teachers = teacherService.list(wrapper);
        return R.ok().data("teacherList", teachers);
    }
}
