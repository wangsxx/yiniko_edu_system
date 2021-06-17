package com.wangs.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.wangs.oss.service.OssService;
import com.wangs.oss.util.ReadProperties;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @File : OssServiceImpl.java
 * @Time : 2021/2/3 14:17
 * @Author: Wangs
 * @Decs :
 */

@Service
public class OssServiceImpl implements OssService {

    // 上传文件到Oss
    public String uploadAvatar(MultipartFile file) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = ReadProperties.END_POINT;
        String accessKeyId = ReadProperties.KEY_ID;
        String accessKeySecret = ReadProperties.KEY_SECRET;
        String bucketname = ReadProperties.BUCKET_NAME;
        // 1. 形成唯一文件名
        String originalFilename = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        // 2. 创建日期文件夹 在oss中 存储的为 文件夹/文件名称
        String dataPaht = new DateTime().toString("yyyy/MM/dd");
        // 3. 拼接名称  2021/02/03/uuid+文件名.jpg
        String filename = dataPaht + "/" + uuid + originalFilename;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 上传文件流
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            // 实现上传 参数：BUCKET_NAME， oss中文件名, 文件流
            ossClient.putObject(bucketname, filename, inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 关闭OSSClient。
        ossClient.shutdown();
        // 拼接访问路径
        String url = "https://" + bucketname + "." + endpoint + "/" + filename;
        return url;
    }
}
