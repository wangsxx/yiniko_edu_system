package com.wangs.eduservice.entity.vo;

/**
 * @File : TeacherQuery.java
 * @Time : 2021/1/26 16:41
 * @Author: Wangs
 * @Decs :
 */
public class TeacherQuery {

    private String name;
    private Integer level;
    private String begin;
    private String end;

    @Override
    public String toString() {
        return "TeacherQuery{" +
                "name='" + name + '\'' +
                ", level=" + level +
                ", begin='" + begin + '\'' +
                ", end='" + end + '\'' +
                '}';
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
