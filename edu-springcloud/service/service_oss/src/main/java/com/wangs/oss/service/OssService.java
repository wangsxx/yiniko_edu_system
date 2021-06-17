package com.wangs.oss.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @File : OssService.java
 * @Time : 2021/2/3 14:17
 * @Author: Wangs
 * @Decs :
 */

public interface OssService {
    String uploadAvatar(MultipartFile file);
}
