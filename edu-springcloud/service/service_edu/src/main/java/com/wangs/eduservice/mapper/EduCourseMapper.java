package com.wangs.eduservice.mapper;

import com.wangs.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wangs.eduservice.entity.vo.PublishVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author wangs
 * @since 2021-03-09
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    public PublishVo getPublishInfo(String courseId);

}
