package com.wangs.commonutils;

import java.util.HashMap;
import java.util.Map;

/**
 * @File : R.java
 * @Time : 2021/1/26 16:11
 * @Author: Wangs
 * @Decs : 统一返回结果
 */
public class R {
    private Boolean success; // 是否成功
    private Integer code; // 返回码
    private String message; // 返回的消息
    private Map<String, Object> data = new HashMap<String, Object>(); // 返回的数据

    private R() {
    }

    // 成功静态方法
    public static R ok(){
        R r = new R();
        r.setSuccess(true);
        r.setCode(ResultCode.SUCCESS);
        r.setMessage("成功！");
        return r;
    }

    // 失败静态方法
    public static R error(){
        R r = new R();
        r.setSuccess(false);
        r.setCode(ResultCode.ERROR);
        r.setMessage("调用失败！");
        return r;
    }

    // 实现链式调用 R.ok().message(xxx).data(xxx)
    public R success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public R message(String message){
        this.setMessage(message);
        return this;
    }

    public R code(Integer code){
        this.setCode(code);
        return this;
    }

    public R data(String key, Object value){
        this.data.put(key, value);
        return this;
    }

    public R data(Map<String, Object> map){
        this.setData(map);
        return this;
    }



    // getter setter
    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
