package com.wangs.eduservice.service.impl;

import com.wangs.eduservice.entity.EduVideo;
import com.wangs.eduservice.mapper.EduVideoMapper;
import com.wangs.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author wangs
 * @since 2021-03-09
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    public void updateChapter(EduVideo eduVideo) {
        baseMapper.updateById(eduVideo);
    }

    public void delVideo(String videoId) {
        baseMapper.deleteById(videoId);
    }

    public void addVideo(EduVideo eduVideo) {
        baseMapper.insert(eduVideo);
    }

    public EduVideo getVideoById(String videoId) {
        EduVideo video = baseMapper.selectById(videoId);
        return video;
    }
}
