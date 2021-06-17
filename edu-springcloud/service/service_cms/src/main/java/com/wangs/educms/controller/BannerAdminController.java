package com.wangs.educms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangs.commonutils.R;
import com.wangs.educms.entity.CrmBanner;
import com.wangs.educms.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author wangs
 * @since 2021-03-18
 */
@RestController
@RequestMapping("/educms/banner-admin")
@CrossOrigin
public class BannerAdminController {
    @Autowired
    private CrmBannerService bannerService;

    // 1. 分页查询banner
    @GetMapping("pageBanner/{page}/{limit}")
    public R pageBanner(@PathVariable int page, @PathVariable int limit){
        Page<CrmBanner> bannerPage = new Page<CrmBanner>();
        bannerService.page(bannerPage);
        return R.ok().data("items", bannerPage.getRecords()).data("total", bannerPage.getTotal());
    }

    // 2. 添加banner
    @PostMapping("addBanner")
    public R addBanner(CrmBanner banner){
        bannerService.save(banner);
        return R.ok();
    }

    // 3. 修改banner
    @PostMapping("updateBanner")
    public R updateBanner(CrmBanner banner){
        bannerService.updateById(banner);
        return R.ok();
    }

    // 3. 删除banner
    @DeleteMapping("delBanner/{bannerId}")
    public R delBanner(@PathVariable String bannerId){
        bannerService.removeById(bannerId);
        return R.ok();
    }

}

