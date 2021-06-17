package com.wangs.eduservice.controller.front;

import com.wangs.commonutils.R;
import com.wangs.eduservice.entity.EduCourse;
import com.wangs.eduservice.entity.EduTeacher;
import com.wangs.eduservice.service.EduCourseService;
import com.wangs.eduservice.service.EduTeacherService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @File : FrontTeacherController.java
 * @Time : 2021/5/27 11:38
 * @Author: Wangs
 * @Decs :
 */
@RestController
@RequestMapping("/eduservice/frontTeacher")
public class FrontTeacherController {
    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private EduCourseService courseService;

    @GetMapping("teacherDetail/{id}")
    public R teacherDetail(@PathVariable("id") String id){
        EduTeacher teacher = teacherService.getById(id);
        List<EduCourse> course = courseService.getCourseByTeacher(id);
        return R.ok().data("teacherInfo", teacher).data("teacherCourse", course);
    }

}
