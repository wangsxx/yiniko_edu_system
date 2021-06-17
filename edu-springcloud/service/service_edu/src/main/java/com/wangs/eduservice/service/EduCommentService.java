package com.wangs.eduservice.service;

import com.wangs.eduservice.entity.EduComment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author wangs
 * @since 2021-06-01
 */
public interface EduCommentService extends IService<EduComment> {

    Map<String, Object> getCommentByPage(long current, long limit, String courseId);

    void addComment(EduComment comment);
}
