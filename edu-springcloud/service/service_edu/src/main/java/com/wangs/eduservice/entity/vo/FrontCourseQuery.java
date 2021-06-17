package com.wangs.eduservice.entity.vo;

import java.math.BigDecimal;

/**
 * @File : FrontCourseQuery.java
 * @Time : 2021/5/27 16:14
 * @Author: Wangs
 * @Decs :
 */
public class FrontCourseQuery {
    private String oneSubjectId;
    private String twoSubjectId;
    private Boolean priceSort;
    private Boolean buyCountSort;
    private Boolean viewCountSort;
    private Boolean gmtCreatSort;

    public String getOneSubjectId() {
        return oneSubjectId;
    }

    public void setOneSubjectId(String oneSubjectId) {
        this.oneSubjectId = oneSubjectId;
    }

    public String getTwoSubjectId() {
        return twoSubjectId;
    }

    public void setTwoSubjectId(String twoSubjectId) {
        this.twoSubjectId = twoSubjectId;
    }

    public Boolean getPriceSort() {
        return priceSort;
    }

    public void setPriceSort(Boolean priceSort) {
        this.priceSort = priceSort;
    }

    public Boolean getBuyCountSort() {
        return buyCountSort;
    }

    public void setBuyCountSort(Boolean buyCountSort) {
        this.buyCountSort = buyCountSort;
    }

    public Boolean getViewCountSort() {
        return viewCountSort;
    }

    public void setViewCountSort(Boolean viewCountSort) {
        this.viewCountSort = viewCountSort;
    }

    public Boolean getGmtCreatSort() {
        return gmtCreatSort;
    }

    public void setGmtCreatSort(Boolean gmtCreatSort) {
        this.gmtCreatSort = gmtCreatSort;
    }
}
