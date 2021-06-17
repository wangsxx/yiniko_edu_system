package com.wangs.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wangs.eduservice.entity.EduSubject;
import com.wangs.eduservice.entity.excel.SubjectData;
import com.wangs.eduservice.entity.subject.oneSubject;
import com.wangs.eduservice.entity.subject.twoSubject;
import com.wangs.eduservice.listener.SubjectExcelListener;
import com.wangs.eduservice.mapper.EduSubjectMapper;
import com.wangs.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author wangs
 * @since 2021-03-01
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    public void addSubject(MultipartFile file, EduSubjectService subjectService) {
        // 根据文件流的方式读取
        try {
            InputStream in = file.getInputStream();
            EasyExcel.read(in, SubjectData.class, new SubjectExcelListener(subjectService)).sheet().doRead();
        }catch (Exception e){

        }


    }

    public List<oneSubject> getAllSubject() {
        // 查询条件：查询一级分类，parent_id 为0
        QueryWrapper<EduSubject> wrapper1 = new QueryWrapper<EduSubject>();
        wrapper1.eq("parent_id", '0');
        // mapper注入，继承的时候自动注入了baseMapper
        List<EduSubject> oneSubjects = baseMapper.selectList(wrapper1);

        // 查询二级分类
        QueryWrapper<EduSubject> wrapper2 = new QueryWrapper<EduSubject>();
        wrapper1.ne("parent_id", '0');
        // mapper注入，继承的时候自动注入了baseMapper
        List<EduSubject> twoSubjects = baseMapper.selectList(wrapper2);

        // 封装数据
        ArrayList<oneSubject> res = new ArrayList<oneSubject>();
        for(EduSubject oneSubject: oneSubjects){
            oneSubject tmp = new oneSubject();
            tmp.setId(oneSubject.getId());
            tmp.setTitle(oneSubject.getTitle());
            ArrayList<twoSubject> two = new ArrayList<twoSubject>();  // 用于存储二级分类

            for(EduSubject twoSubject: twoSubjects){
                // 判断parent_id 和当前的id是否相等
                if(twoSubject.getParentId().equals(tmp.getId())){
                    // 存储二级分类
                    twoSubject tmp2 = new twoSubject();
                    tmp2.setId(twoSubject.getId());
                    tmp2.setTitle(twoSubject.getTitle());
                    two.add(tmp2);
                }
            }
            tmp.setChildren(two);
            res.add(tmp);
        }
        return res;
    }
}
