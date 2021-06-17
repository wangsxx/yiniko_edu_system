package com.wangs.eduservice.controller;


import com.wangs.commonutils.R;
import com.wangs.eduservice.entity.EduVideo;
import com.wangs.eduservice.nacosClient.VideoClient;
import com.wangs.eduservice.service.EduVideoService;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author wangs
 * @since 2021-03-09
 */
@RestController
@RequestMapping("/eduservice/edu-video")
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;

    // 注入接口，调用其他服务
    @Autowired
    private VideoClient videoClient;

    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        videoService.addVideo(eduVideo);
        return R.ok();
    }

    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo){
        videoService.updateChapter(eduVideo);
        return R.ok();
    }

    // TODO:后续需要完善，这里删除小节的同时需要删除视频
    @DeleteMapping("delVideo/{videoId}")
    public R delVideo(@PathVariable String videoId){
        EduVideo video = videoService.getVideoById(videoId);
        String AlyVideoId = video.getVideoSourceId();
        if(!StringUtils.isEmpty(AlyVideoId)){
            R r = videoClient.delVideo(AlyVideoId);
            if (r.getCode() == 2001){
                System.out.println("熔断器，删除视频失败");
                throw new RuntimeException("服务端测试异常！");
            }
        }
        videoService.delVideo(videoId);
        return R.ok();
    }

    @GetMapping("getVideoById/{videoId}")
    public R getVideoById(@PathVariable String videoId){
        EduVideo video = videoService.getVideoById(videoId);
        return R.ok().data("video", video);
    }

}

