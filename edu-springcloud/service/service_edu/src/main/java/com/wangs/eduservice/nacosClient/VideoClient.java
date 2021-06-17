package com.wangs.eduservice.nacosClient;

import com.netflix.client.ClientException;
import com.wangs.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @File : videoClient.java
 * @Time : 2021/3/15 20:59
 * @Author: Wangs
 * @Decs :
 */
@FeignClient(name = "service-video", fallback = VideoClientImpl.class)  // 这里要加上服务名
@Component
public interface VideoClient {
    // 删除阿里云上的视频
    @DeleteMapping("eduvideo/video/delVideo/{videoId}")  // 这里的路径全部都要
    public R delVideo(@PathVariable("videoId") String videoId);

    // 根据课程，删除全部阿里云上的视频
    @DeleteMapping("eduvideo/video/delAllVideo")
    public R delAllVideo(@RequestParam("videoIdList") List<String> videoIdList);
}
