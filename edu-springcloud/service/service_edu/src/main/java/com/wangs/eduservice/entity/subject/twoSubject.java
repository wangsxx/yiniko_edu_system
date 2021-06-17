package com.wangs.eduservice.entity.subject;

/**
 * @File : twoSubject.java
 * @Time : 2021/3/8 12:58
 * @Author: Wangs
 * @Decs :
 */
public class twoSubject {
    private String id;
    private String title;

    @Override
    public String toString() {
        return "twoSubject{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

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
