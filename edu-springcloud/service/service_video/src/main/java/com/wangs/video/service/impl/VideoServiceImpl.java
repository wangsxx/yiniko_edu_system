package com.wangs.video.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.wangs.video.service.VideoService;
import com.wangs.video.util.ReadProperties;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @File : VideoServiceImpl.java
 * @Time : 2021/3/15 15:03
 * @Author: Wangs
 * @Decs :
 */
@Service
public class VideoServiceImpl implements VideoService {
    public String uploadVideo(MultipartFile file) throws IOException {
        String accessKeyId = ReadProperties.KEY_ID;
        String accessKeySecret = ReadProperties.KEY_SECRET;
        String fileName = file.getOriginalFilename();
        InputStream inputStream = file.getInputStream();
        String title = fileName.substring(0, fileName.lastIndexOf('.'));  // 不要后缀

        UploadStreamRequest request = new UploadStreamRequest(accessKeyId, accessKeySecret, title, fileName, inputStream);
        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadStreamResponse response = uploader.uploadStream(request);

        return response.getVideoId();
    }

    public void delVideoById(String videoId) throws ClientException {
        String accessKeyId = ReadProperties.KEY_ID;
        String accessKeySecret = ReadProperties.KEY_SECRET;

        String regionId = "cn-shanghai";  // 点播服务接入区域
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);

        DeleteVideoResponse response = new DeleteVideoResponse();
        try {
            DeleteVideoRequest request = new DeleteVideoRequest();
            //支持传入多个视频ID，多个用逗号分隔
            request.setVideoIds(videoId);
            response = client.getAcsResponse(request);
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
        System.out.print("RequestId = " + response.getRequestId() + "\n");
    }

    public void delVideosByIds(List<String> videoIdList) {
        String accessKeyId = ReadProperties.KEY_ID;
        String accessKeySecret = ReadProperties.KEY_SECRET;

        String regionId = "cn-shanghai";  // 点播服务接入区域
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);

        String ids = StringUtils.join(videoIdList, ",");  // 字符串拼接
        System.out.println(ids);
        DeleteVideoResponse response = new DeleteVideoResponse();
        try {
            DeleteVideoRequest request = new DeleteVideoRequest();
            //支持传入多个视频ID，多个用逗号分隔
            request.setVideoIds(ids);
            response = client.getAcsResponse(request);
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
        System.out.print("RequestId = " + response.getRequestId() + "\n");
    }

    public String getPlayAuth(String videoId) {
        String accessKeyId = ReadProperties.KEY_ID;
        String accessKeySecret = ReadProperties.KEY_SECRET;
        // 创建初始化对象
        DefaultAcsClient client = null;
        try {
            client = initVodClient(accessKeyId, accessKeySecret);
        } catch (ClientException e) {
            e.printStackTrace();
        }

        // 创建获取视频地址的request和response
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();  // 这里的类不一样 之前的是 GetPlayInfoRequest

        // 向request中设置视频ID
        request.setVideoId(videoId);

        // 调用初始化对象里面的方法，传递request，获取数据
        GetVideoPlayAuthResponse response= null;
        try {
            response = client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return response.getPlayAuth();
    }

    public static DefaultAcsClient initVodClient(String accessKeyId, String accessKeySecret) throws ClientException {
        String regionId = "cn-shanghai";  // 点播服务接入区域
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        return client;
    }
}
