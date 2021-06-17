package com.wangs.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangs.commonutils.R;
import com.wangs.eduservice.entity.EduCourse;
import com.wangs.eduservice.entity.subject.oneSubject;
import com.wangs.eduservice.entity.vo.CourseQuery;
import com.wangs.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author wangs
 * @since 2021-03-01
 */
@RestController
@RequestMapping("/eduservice/edu-subject")
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;

    // 添加课程分类
    // 获取上传过来的文件，把文件内容读取出来
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file){
        // 上传的excel文件
        subjectService.addSubject(file, subjectService);
        return R.ok();
    }

    // 按固定格式返回课程分类数据
    @GetMapping("getSubject")
    public R getSubject(){
        // 一级分类实体中包含了二级分类
        List<oneSubject> list = subjectService.getAllSubject();
        return R.ok().data("subjectList", list);
    }

}

