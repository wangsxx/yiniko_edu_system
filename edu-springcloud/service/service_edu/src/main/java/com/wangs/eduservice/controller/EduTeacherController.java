package com.wangs.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangs.commonutils.R;
import com.wangs.eduservice.entity.EduTeacher;
import com.wangs.eduservice.entity.vo.TeacherQuery;
import com.wangs.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author wangs
 * @since 2021-01-26
 */
@RestController
@RequestMapping("/eduservice/edu-teacher")
public class EduTeacherController {

    @Autowired
    EduTeacherService eduTeacherService;

    // 查询所有的讲师信息
    @GetMapping("/getAll")
    public R getAll(){
        List<EduTeacher> teachers = eduTeacherService.list(null);
        return R.ok().data("items", teachers);
    }

    // 讲师逻辑删除
    @DeleteMapping("/delTeacher/{id}")
    public R delTeacher(@PathVariable("id") String id){
        boolean b = eduTeacherService.removeById(id);
        if(b){
            return R.ok();
        }else {
            return R.error();
        }
    }

    // 分页查询
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable("current") long current, @PathVariable("limit") long limit){
        // 创建page对象
        Page<EduTeacher> eduTeacherPage = new Page<EduTeacher>(current, limit);
        eduTeacherService.page(eduTeacherPage,null);  // 封装给eduTeacherPage对象

        long total = eduTeacherPage.getTotal();
        List<EduTeacher> records = eduTeacherPage.getRecords();

        return R.ok().data("total", total).data("records", records);
    }

    // 组合条件查询带分页
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable("current") long current,
                                  @PathVariable("limit") long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery){
        // 创建page对象
        Page<EduTeacher> eduTeacherPage = new Page<EduTeacher>(current, limit);
        // 创建wrapper
        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<EduTeacher>();
        // 使用动态sql 组合查询
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        if (!StringUtils.isEmpty(name)){
            queryWrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level)){
            queryWrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)){
            queryWrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)){
            queryWrapper.le("gmt_create", end);
        }
        queryWrapper.orderByDesc("gmt_create");
        eduTeacherService.page(eduTeacherPage,queryWrapper);  // 封装给eduTeacherPage对象

        long total = eduTeacherPage.getTotal();
        List<EduTeacher> records = eduTeacherPage.getRecords();

        return R.ok().data("total", total).data("records", records);
    }

    // 添加教师
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean b = eduTeacherService.save(eduTeacher);
        if(b){
            return R.ok();
        }else {
            return R.error();
        }
    }

    // 根据ID查询教师
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable("id") long id){
        EduTeacher teacher = eduTeacherService.getById(id);
        return R.ok().data("teacher",teacher);
    }

    // 修改教师, 需要传递ID值
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher){
        boolean b = eduTeacherService.updateById(eduTeacher);
        if(b){
            return R.ok();
        }else {
            return R.error();
        }
    }

}

