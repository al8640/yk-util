package com.yangke.base.annotation;

import java.lang.annotation.*;


/**
 * @author ke.yang1
 * @description 验证token合法性的注解类
 * @date 2020/8/6 1:44 下午
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SessionAuth {
    String url();

    int tokenArgIndex();

    boolean whiteList() default false;
}
