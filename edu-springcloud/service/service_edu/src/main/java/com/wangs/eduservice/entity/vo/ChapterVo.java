package com.wangs.eduservice.entity.vo;

import java.util.List;

/**
 * @File : ChapterVo.java
 * @Time : 2021/3/10 10:30
 * @Author: Wangs
 * @Decs :
 */
public class ChapterVo {

    private String id;
    private String title;
    private List<VideoVo> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<VideoVo> getChildren() {
        return children;
    }

    public void setChildren(List<VideoVo> children) {
        this.children = children;
    }
}
