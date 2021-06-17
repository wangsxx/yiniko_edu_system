package com.wangs.eduservice.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * @File : SubjectData.java
 * @Time : 2021/3/1 15:30
 * @Author: Wangs
 * @Decs :
 */
public class SubjectData {
    @ExcelProperty(index=0)
    private String oneSubjectName;
    @ExcelProperty(index=1)
    private String twoSubjectName;

    public SubjectData(String oneSubjectName, String twoSubjectName) {
        this.oneSubjectName = oneSubjectName;
        this.twoSubjectName = twoSubjectName;
    }

    public SubjectData() {
    }

    public String getOneSubjectName() {
        return oneSubjectName;
    }

    public void setOneSubjectName(String oneSubjectName) {
        this.oneSubjectName = oneSubjectName;
    }

    public String getTwoSubjectName() {
        return twoSubjectName;
    }

    public void setTwoSubjectName(String twoSubjectName) {
        this.twoSubjectName = twoSubjectName;
    }
}
