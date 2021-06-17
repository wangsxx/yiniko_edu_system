package com.wangs.excel;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * @File : DemoData.java
 * @Time : 2021/3/1 11:35
 * @Author: Wangs
 * @Decs :
 */

public class DemoData {
    // 表头
    @ExcelProperty(value = "学生编号", index = 0)
    private Integer sno;
    @ExcelProperty(value = "学生姓名", index = 1)
    private String sname;

    public DemoData() {
    }

    @Override
    public String toString() {
        return "DemoData{" +
                "sno=" + sno +
                ", sname='" + sname + '\'' +
                '}';
    }

    public DemoData(Integer sno, String sname) {
        this.sno = sno;
        this.sname = sname;
    }

    public Integer getSno() {
        return sno;
    }

    public void setSno(Integer sno) {
        this.sno = sno;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }
}
