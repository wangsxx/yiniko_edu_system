package com.wangs.eduservice.nacosClient;

import com.wangs.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @File : VideoClientImpl.java
 * @Time : 2021/3/16 14:27
 * @Author: Wangs
 * @Decs :
 */
@Component
public class VideoClientImpl implements VideoClient {
    public R delVideo(String videoId) {
        return R.error().data("msg", "删除视频出错");
    }

    public R delAllVideo(List<String> videoIdList) {
        return R.error().data("msg", "删除多个视频出错");
    }
}
