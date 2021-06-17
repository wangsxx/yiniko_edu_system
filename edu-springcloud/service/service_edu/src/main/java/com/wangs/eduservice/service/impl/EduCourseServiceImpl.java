package com.wangs.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangs.eduservice.entity.EduChapter;
import com.wangs.eduservice.entity.EduCourse;
import com.wangs.eduservice.entity.EduCourseDescription;
import com.wangs.eduservice.entity.EduVideo;
import com.wangs.eduservice.entity.vo.CourseInfoVo;
import com.wangs.eduservice.entity.vo.FrontCourseQuery;
import com.wangs.eduservice.entity.vo.PublishVo;
import com.wangs.eduservice.mapper.EduCourseMapper;
import com.wangs.eduservice.service.EduChapterService;
import com.wangs.eduservice.service.EduCourseDescriptionService;
import com.wangs.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangs.eduservice.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author wangs
 * @since 2021-03-09
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    // 注入edu_course_description_service
    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;
    @Autowired
    private EduChapterService chapterService;
    @Autowired
    private EduVideoService videoService;

    // 添加课程基本信息
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        // 1. 向课程表中添加信息
        // 提取信息，封装course实体
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int insert = baseMapper.insert(eduCourse);   // 这里直接使用的mapper

        String cid = eduCourse.getId();
        // 2. 向课程描述表中添加
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        eduCourseDescription.setId(cid);  // 这里id不使用自动生成的，而必须和课程id保持一直
        eduCourseDescriptionService.save(eduCourseDescription);  // 这里是save 封装了mapper中的insert
        return cid;
    }

    public CourseInfoVo getCourseById(String id) {
        // 查找课程表
        EduCourse eduCourse = baseMapper.selectById(id);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse, courseInfoVo);
        // 查找描述表
        EduCourseDescription courseDes = eduCourseDescriptionService.getById(id);
        courseInfoVo.setDescription(courseDes.getDescription());
        return courseInfoVo;
    }

    public void updateCourse(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        EduCourseDescription courseDes = new EduCourseDescription();
        courseDes.setId(courseInfoVo.getId());
        courseDes.setDescription(courseInfoVo.getDescription());
        // 更新
        baseMapper.updateById(eduCourse);
        eduCourseDescriptionService.updateById(courseDes);
    }

    public PublishVo getPublish(String courseId) {
        PublishVo publishInfo = baseMapper.getPublishInfo(courseId);
        return publishInfo;
    }

    public void publish(EduCourse course) {
        baseMapper.updateById(course);
    }

    public void delCourse(String courseId) {
        // 1. 删除课程表内信息 course
        baseMapper.deleteById(courseId);
        // 2. 删除描述表内信息 description
        eduCourseDescriptionService.removeById(courseId);
        // 3. 根据CourseID 删除章节表内信息 chapter
        QueryWrapper<EduChapter> chapterQueryWrapper = new QueryWrapper<EduChapter>();
        chapterQueryWrapper.eq("course_id", courseId);
        chapterService.remove(chapterQueryWrapper);
        // 4. 根据CourseID 删除小节表内信息 video
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<EduVideo>();
        videoQueryWrapper.eq("course_id", courseId);
        videoService.remove(videoQueryWrapper);
    }

    public List<EduCourse> getCourseByTeacher(String id) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<EduCourse>();
        wrapper.eq("teacher_id", id);
        List<EduCourse> courseList = baseMapper.selectList(wrapper);
        return courseList;
    }

    public HashMap<String, Object> getFrontCourseByCondition(Page<EduCourse> pageCourse, FrontCourseQuery courseQuery) {
        // 创建wrapper
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<EduCourse>();
        // 使用动态sql 组合查询
        String oneSubjectId = courseQuery.getOneSubjectId();
        String twoSubjectId = courseQuery.getTwoSubjectId();
        Boolean buyCountSort = courseQuery.getBuyCountSort();
        Boolean priceSort = courseQuery.getPriceSort();
        Boolean viewCountSort = courseQuery.getViewCountSort();
        Boolean timeSort = courseQuery.getGmtCreatSort();

        if (!StringUtils.isEmpty(oneSubjectId)){
            queryWrapper.eq("subject_parent_id", oneSubjectId);
        }
        if (!StringUtils.isEmpty(twoSubjectId)){
            queryWrapper.eq("subject_id", twoSubjectId);
        }
        if (buyCountSort){
            queryWrapper.orderByDesc("buy_count");
        }
        if (priceSort){
            queryWrapper.orderByDesc("price");
        }
        if (viewCountSort){
            queryWrapper.orderByDesc("view_count");
        }
        if(timeSort){
            queryWrapper.orderByDesc("gmt_create");
        }

        this.page(pageCourse, queryWrapper);  // 封装给eduTeacherPage对象

        long total = pageCourse.getTotal();
        List<EduCourse> records = pageCourse.getRecords();
        System.out.println(total);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("total", total);
        map.put("records", records);

        return map;
    }
}
