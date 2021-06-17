package com.wangs;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @File : videoTest.java
 * @Time : 2021/3/15 10:49
 * @Author: Wangs
 * @Decs :
 */

public class videoTest {

    // 根据视频ID获得视频地址
    @Test
    public void videoAdressTest() throws ClientException {
        // 创建初始化对象
        DefaultAcsClient client = initVodClient("XXX", "XXX");

        // 创建获取视频地址的request和response
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();

        // 向request中设置视频ID
        request.setVideoId("d879b9bb412e4cedaa149887a1e73592");

        // 调用初始化对象里面的方法，传递request，获取数据
        response= client.getAcsResponse(request);

        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        //播放地址
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
        }
        System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
    }


    // 根据视频ID获得视频凭证
    @Test
    public void videoPZTest() throws ClientException {
        // 创建初始化对象
        DefaultAcsClient client = initVodClient("XXX", "XXX");

        // 创建获取视频地址的request和response
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();  // 这里的类不一样 之前的是 GetPlayInfoRequest

        // 向request中设置视频ID
        request.setVideoId("d4fdb1b2d1ef4ed88285ec79946e3915");

        // 调用初始化对象里面的方法，传递request，获取数据
        GetVideoPlayAuthResponse response= client.getAcsResponse(request);

        //播放凭证
        System.out.print("PlayAuth = " + response.getPlayAuth() + "\n");
        //VideoMeta信息
        System.out.print("VideoMeta.Title = " + response.getVideoMeta().getTitle() + "\n");
    }

    // 上传视频: 本地文件上传
    @Test
    public void uploadVideo(){
        UploadVideoRequest request = new UploadVideoRequest("XXX",
                "XXX",
                "test",  // 阿里云的文件名称
                "D:\\8JavaProject\\6 - What If I Want to Move Faster.mp4");  // 本地文件地址+名称
        /*可指定分片上传时每个分片的大小，默认为1M字节*/
        request.setPartSize(1 * 1024 * 1024L);
        /*可指定分片上传时的并发线程数，默认为1（注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
        request.setTaskNum(1);

        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);
        System.out.print("RequestId="  + response.getRequestId() +  "\n");  //请求视频点播服务的请求ID
        if (response.isSuccess()) {
            System.out.print("VideoId=" +  response.getVideoId() +  "\n");
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId="  + response.getVideoId() +  "\n");
            System.out.print("ErrorCode=" +  response.getCode() +  "\n");
            System.out.print("ErrorMessage=" +  response.getMessage()  + "\n");
        }
    }

    @Test
    public void deleteVideo(){
        List<String> a = new ArrayList<String>();
        a.add("1");
        a.add("2");
        a.add("3");
        System.out.println(StringUtils.join(a, ","));
    }


    public static DefaultAcsClient initVodClient(String accessKeyId, String accessKeySecret) throws ClientException {
        String regionId = "cn-shanghai";  // 点播服务接入区域
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        return client;
    }
}
