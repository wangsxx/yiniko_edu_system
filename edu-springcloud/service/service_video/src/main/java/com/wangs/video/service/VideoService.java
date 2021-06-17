package com.wangs.video.service;

import com.aliyuncs.exceptions.ClientException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @File : VideoService.java
 * @Time : 2021/3/15 15:03
 * @Author: Wangs
 * @Decs :
 */
public interface VideoService {
    String uploadVideo(MultipartFile file) throws IOException;

    void delVideoById(String videoId) throws ClientException;

    void delVideosByIds(List<String> videoIdList);

    String getPlayAuth(String videoId);
}
