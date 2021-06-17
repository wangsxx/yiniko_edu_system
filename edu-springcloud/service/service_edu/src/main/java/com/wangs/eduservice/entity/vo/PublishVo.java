package com.wangs.eduservice.entity.vo;

/**
 * @File : PublishVo.java
 * @Time : 2021/3/11 15:13
 * @Author: Wangs
 * @Decs :
 */
public class PublishVo {

    private String id;
    private String title;
    private String cover;
    private Integer lessonNum;
    private String OneSubject;
    private String TwoSubject;
    private String teacherName;
    private String price;
    private String description;

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

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Integer getLessonNum() {
        return lessonNum;
    }

    public void setLessonNum(Integer lessonNum) {
        this.lessonNum = lessonNum;
    }

    public String getOneSubject() {
        return OneSubject;
    }

    public void setOneSubject(String oneSubject) {
        OneSubject = oneSubject;
    }

    public String getTwoSubject() {
        return TwoSubject;
    }

    public void setTwoSubject(String twoSubject) {
        TwoSubject = twoSubject;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
