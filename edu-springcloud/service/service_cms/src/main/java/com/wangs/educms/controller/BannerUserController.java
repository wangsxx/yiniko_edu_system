package com.wangs.educms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangs.commonutils.R;
import com.wangs.educms.entity.CrmBanner;
import com.wangs.educms.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author wangs
 * @since 2021-03-18
 */
@RestController
@RequestMapping("/educms/banner-user")
@CrossOrigin
public class BannerUserController {

    @Autowired
    private CrmBannerService bannerService;

    @GetMapping("getAllBanner")
    public R getAllBanner(){
        List<CrmBanner> banners = bannerService.selectAllBanner();  // 这里不直接调用bannerService中的接口是为了方便后面使用Redis
        return R.ok().data("bannerList", banners);
    }
}

