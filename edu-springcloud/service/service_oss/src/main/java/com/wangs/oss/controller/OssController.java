package com.wangs.oss.controller;

import com.wangs.commonutils.R;
import com.wangs.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @File : OssController.java
 * @Time : 2021/2/3 14:17
 * @Author: Wangs
 * @Decs :
 */
@RestController
@RequestMapping("/eduoss/fileoss")
@CrossOrigin
public class OssController {

    @Autowired
    private OssService ossService;

    // 上传头像方法
    @PostMapping
    public R uploadFile(MultipartFile file){
        String url = ossService.uploadAvatar(file);
        return R.ok().data("url", url);
    }


}
