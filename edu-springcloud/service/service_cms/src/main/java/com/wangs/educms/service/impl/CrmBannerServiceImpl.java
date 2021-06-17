package com.wangs.educms.service.impl;

import com.wangs.educms.entity.CrmBanner;
import com.wangs.educms.mapper.CrmBannerMapper;
import com.wangs.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author wangs
 * @since 2021-03-18
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Cacheable(value = "banner", key = "'selectBanner'")
    public List<CrmBanner> selectAllBanner() {
        List<CrmBanner> banners = this.baseMapper.selectList(null);
        return banners;
    }
}
