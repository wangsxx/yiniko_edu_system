package com.wangs.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

/**
 * @File : TestEasyExcel.java
 * @Time : 2021/3/1 11:36
 * @Author: Wangs
 * @Decs :
 */
public class TestEasyExcel {
    public static void main(String[] args) {
        // 实现easyexcel写操作
        // 1.设置路径和文件名
        String filename = "write.xlsx";
        // 2. 调用方法写操作 第一个参数：路径文件名 第二个参数：实体类
        //EasyExcel.write(filename, DemoData.class).sheet("学生列表").doWrite(getData());

        // 2. 读操作
        EasyExcel.read(filename, DemoData.class, new ExcelListener()).sheet().doRead();
    }

    private static List<DemoData> getData(){
        List<DemoData> list = new ArrayList<DemoData>();
        for (int i = 0; i<100; i++){
            DemoData demoData = new DemoData();
            demoData.setSno(i);
            demoData.setSname("wangs" + i);
            list.add(demoData);
        }
        return list;
    }
}
