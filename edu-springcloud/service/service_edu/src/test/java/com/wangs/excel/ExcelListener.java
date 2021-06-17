package com.wangs.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

/**
 * @File : ExcelListener.java
 * @Time : 2021/3/1 11:47
 * @Author: Wangs
 * @Decs :
 */
public class ExcelListener extends AnalysisEventListener<DemoData> {
    // 一行一行的读取excel内容
    public void invoke(DemoData demoData, AnalysisContext analysisContext) {
        System.out.println("******" + demoData);
    }

    // 读取表头信息
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头信息：" + headMap);
    }

    // 读取完成之后
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
