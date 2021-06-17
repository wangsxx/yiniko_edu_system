package com.wangs.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangs.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wangs.eduservice.entity.vo.CourseInfoVo;
import com.wangs.eduservice.entity.vo.FrontCourseQuery;
import com.wangs.eduservice.entity.vo.PublishVo;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author wangs
 * @since 2021-03-09
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseById(String id);

    void updateCourse(CourseInfoVo courseInfoVo);

    PublishVo getPublish(String courseId);

    void publish(EduCourse course);

    void delCourse(String courseId);

    List<EduCourse> getCourseByTeacher(String id);

    HashMap<String, Object> getFrontCourseByCondition(Page<EduCourse> pageCourse, FrontCourseQuery courseQuery);
}
