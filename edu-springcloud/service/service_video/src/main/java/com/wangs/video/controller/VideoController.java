package com.wangs.video.controller;

import com.aliyuncs.exceptions.ClientException;
import com.wangs.commonutils.R;
import com.wangs.video.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @File : VideoController.java
 * @Time : 2021/3/15 15:02
 * @Author: Wangs
 * @Decs :
 */

@RestController
@RequestMapping("eduvideo/video")
@CrossOrigin
public class VideoController {

    @Autowired
    private VideoService videoService;

    // 上传视频到阿里云
    @PostMapping("uploadVideo")
    public R uploadVideo(MultipartFile file) throws IOException {
        String videoId = videoService.uploadVideo(file);
        return R.ok().data("videoId", videoId);
    }

    // 删除阿里云上的视频
    @DeleteMapping("delVideo/{videoId}")
    public R delVideo(@PathVariable String videoId) throws ClientException {
        videoService.delVideoById(videoId);
        return R.ok();
    }

    // 删除阿里云上所有的视频，通过id列表 List<String>
    @DeleteMapping("delAllVideo")
    public R delAllVideo(@RequestParam("videoIdList")List<String> videoIdList) throws ClientException {
        videoService.delVideosByIds(videoIdList);
        return R.ok();
    }

    // 根据视频ID获得视频凭证
    @GetMapping("getPlayAuth/{videoId}")
    public R getPlayAuth(@PathVariable("videoId") String videoId){
        String videoAuth = videoService.getPlayAuth(videoId);
        return R.ok().data("playAuth", videoAuth);
    }

}
