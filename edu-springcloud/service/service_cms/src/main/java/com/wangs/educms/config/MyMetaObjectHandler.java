package com.wangs.educms.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @File : MyMetaObjectHandler.java
 * @Time : 2021/1/27 12:33
 * @Author: Wangs
 * @Decs : 自动填充
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    // 插入自动填充
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("isDeleted",false,metaObject);
        this.setFieldValByName("gmtCreate",new Date(),metaObject);
        this.setFieldValByName("gmtModified",new Date(),metaObject);

    }

    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("gmtModified",new Date(),metaObject);
    }
}
