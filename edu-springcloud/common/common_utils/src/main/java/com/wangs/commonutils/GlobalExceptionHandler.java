package com.wangs.commonutils;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @File : GlobalExceptionHandler.java
 * @Time : 2021/1/27 12:57
 * @Author: Wangs
 * @Decs :
 */

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e){
        e.printStackTrace();
        return R.error().message("执行全局异常处理");
    }
}
