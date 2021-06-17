package com.wangs.eduservice.entity.vo;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @File : CourseInfoVo.java
 * @Time : 2021/3/9 10:40
 * @Author: Wangs
 * @Decs :
 */
public class CourseInfoVo {

    private String id;  // 课程id
    private String teacherId;  // 讲师id
    private String subjectId;  // 分类ID
    private String subjectParentId;
    private String title;  // 课程名称
    private BigDecimal price;  // 课程价格
    private Integer lessonNum;  // 总课时
    private String cover;  // 封面路径
    private String description;  // 课程描述

    public String getSubjectParentId() {
        return subjectParentId;
    }

    public void setSubjectParentId(String subjectParentId) {
        this.subjectParentId = subjectParentId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getLessonNum() {
        return lessonNum;
    }

    public void setLessonNum(Integer lessonNum) {
        this.lessonNum = lessonNum;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
