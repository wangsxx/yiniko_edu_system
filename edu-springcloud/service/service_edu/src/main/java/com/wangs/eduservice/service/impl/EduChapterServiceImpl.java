package com.wangs.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wangs.eduservice.entity.EduChapter;
import com.wangs.eduservice.entity.EduVideo;
import com.wangs.eduservice.entity.vo.ChapterVo;
import com.wangs.eduservice.entity.vo.VideoVo;
import com.wangs.eduservice.mapper.EduChapterMapper;
import com.wangs.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangs.eduservice.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author wangs
 * @since 2021-03-09
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService eduVideoService;

    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<EduChapter>();
        wrapperChapter.eq("course_id", courseId);
        List<EduChapter> eduChapters = baseMapper.selectList(wrapperChapter);
        
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<EduVideo>();
        wrapperVideo.eq("course_id", courseId);
        List<EduVideo> eduVideos = eduVideoService.list(wrapperVideo);

        ArrayList<ChapterVo> res = new ArrayList<ChapterVo>();
        for(EduChapter eduChapter: eduChapters){
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter, chapterVo);
            List<VideoVo> videoVos = new ArrayList<VideoVo>();

            for(EduVideo eduVideo: eduVideos){
                // 判断chapter_id是否相等
                if(eduVideo.getChapterId().equals(chapterVo.getId())){
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo, videoVo);
                    videoVos.add(videoVo);
                }
            }
            chapterVo.setChildren(videoVos);
            res.add(chapterVo);
        }
        return res;
    }

    public void addChapter(EduChapter eduChapter) {
        baseMapper.insert(eduChapter);
    }

    public EduChapter getChapterById(String chapterId) {
        EduChapter eduChapter = baseMapper.selectById(chapterId);
        return eduChapter;
    }

    public void updateChapter(EduChapter eduChapter) {
        baseMapper.updateById(eduChapter);
    }

    public void delChapter(String chapterId) {
        // 这里删除直接全删
        baseMapper.deleteById(chapterId);  // 删除章节
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<EduVideo>();
        wrapper.eq("chapter_id", chapterId);
        eduVideoService.remove(wrapper);
    }
}
