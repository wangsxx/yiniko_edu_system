package com.wangs.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangs.commonutils.R;
import com.wangs.eduservice.entity.EduComment;
import com.wangs.eduservice.service.EduCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author wangs
 * @since 2021-06-01
 */
@RestController
@RequestMapping("/eduservice/edu-comment")
public class EduCommentController {

    @Autowired
    private EduCommentService commentService;

    @GetMapping("getCommentByPage/{courseId}/{current}/{limit}")
    public R getCommentByPage(@PathVariable("courseId") String courseId,
                              @PathVariable("current") long current,
                              @PathVariable("limit") long limit){

        Map<String, Object> resutlt = commentService.getCommentByPage(current, limit, courseId);
        return R.ok().data("total", resutlt.get("total")).data("records", resutlt.get("records"));
    }

    @PostMapping("addComment")
    public R addComment(@RequestBody EduComment comment){
        commentService.addComment(comment);
        return R.ok().data("msg", "评论成功！");
    }

}

