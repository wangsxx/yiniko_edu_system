package com.wangs.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wangs.eduservice.entity.EduSubject;
import com.wangs.eduservice.entity.excel.SubjectData;
import com.wangs.eduservice.service.EduSubjectService;
import net.bytebuddy.implementation.bytecode.Throw;

import java.util.Map;

/**
 * @File : SubjectExcelListener.java
 * @Time : 2021/3/1 15:34
 * @Author: Wangs
 * @Decs :
 */
public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    // 这个方法无法交给spring管理，因此无法获取service的bean对象
    // 可以通过构造函数的方法获得
    private EduSubjectService subjectService;

    public SubjectExcelListener() {
    }

    public SubjectExcelListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }

    // 一行一行的读取excel内容
    public void invoke(SubjectData subject, AnalysisContext analysisContext) {
        EduSubject oneSubject = this.existSubject(subjectService, subject.getOneSubjectName(), "0");// 判断一级分类是否重复
        if(oneSubject == null){
            oneSubject = new EduSubject();
            oneSubject.setParentId("0");
            oneSubject.setTitle(subject.getOneSubjectName());
            subjectService.save(oneSubject);
        }

        // 添加二级分类
        EduSubject twoSubject = this.existSubject(subjectService, subject.getTwoSubjectName(), oneSubject.getId());
        if(twoSubject == null){
            twoSubject = new EduSubject();
            twoSubject.setParentId(oneSubject.getId());
            twoSubject.setTitle(subject.getTwoSubjectName());
            subjectService.save(twoSubject);
        }
    }

    // 判断分类是否重复
    private EduSubject existSubject(EduSubjectService eduSubjectService, String name, String parent_id){
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<EduSubject>();
        queryWrapper.eq("title", name);
        queryWrapper.eq("parent_id", parent_id);
        EduSubject eduSubject = subjectService.getOne(queryWrapper);
        return eduSubject;
    }

    // 读取表头信息
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头信息：" + headMap);
    }

    // 读取完成之后
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
