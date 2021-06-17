package com.wangs.eduservice.service;

import com.wangs.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author wangs
 * @since 2021-03-09
 */
public interface EduVideoService extends IService<EduVideo> {

    void updateChapter(EduVideo eduVideo);

    void delVideo(String videoId);

    void addVideo(EduVideo eduVideo);

    EduVideo getVideoById(String videoId);
}
