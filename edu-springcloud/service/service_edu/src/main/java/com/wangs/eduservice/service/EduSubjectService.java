package com.wangs.eduservice.service;

import com.wangs.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wangs.eduservice.entity.subject.oneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author wangs
 * @since 2021-03-01
 */
public interface EduSubjectService extends IService<EduSubject> {

    void addSubject(MultipartFile file, EduSubjectService subjectService);

    List<oneSubject> getAllSubject();
}
