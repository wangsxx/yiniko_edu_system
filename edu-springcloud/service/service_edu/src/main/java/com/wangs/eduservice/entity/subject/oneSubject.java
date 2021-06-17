package com.wangs.eduservice.entity.subject;

import java.util.List;

/**
 * @File : oneSubject.java
 * @Time : 2021/3/8 12:58
 * @Author: Wangs
 * @Decs :
 */
public class oneSubject {
    private String id;
    private String title;

    public List<twoSubject> getChildren() {
        return children;
    }

    public void setChildren(List<twoSubject> children) {
        this.children = children;
    }

    private List<twoSubject> children;


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
}
