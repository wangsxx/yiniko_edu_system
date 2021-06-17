package com.wangs.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangs.eduservice.entity.EduComment;
import com.wangs.eduservice.mapper.EduCommentMapper;
import com.wangs.eduservice.service.EduCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author wangs
 * @since 2021-06-01
 */
@Service
public class EduCommentServiceImpl extends ServiceImpl<EduCommentMapper, EduComment> implements EduCommentService {

    public Map<String, Object> getCommentByPage(long current, long limit, String courseId) {
        Page<EduComment> page = new Page<EduComment>(current, limit);

        QueryWrapper<EduComment> wrapper = new QueryWrapper<EduComment>();
        wrapper.eq("course_id", courseId);
        wrapper.orderByDesc("gmt_create");
        Page<EduComment> commentPage = this.page(page, wrapper);
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("total", commentPage.getTotal());
        result.put("records", commentPage.getRecords());

        return result;
    }

    public void addComment(EduComment comment) {
        baseMapper.insert(comment);
    }
}
