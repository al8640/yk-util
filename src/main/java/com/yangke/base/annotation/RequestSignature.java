package com.yangke.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ke.yang1
 * @description
 * @date 2021/8/4 2:29 下午
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestSignature {
    String FIXED = "sjdxfnqogbzoun13d971ckh8p";

    /**
     * 参与签名的混淆值
     * @return
     */
    String fixed() default FIXED;

    /**
     * 参与签名的参数,-1从request里取
     * @return
     */
    int[] paramIndex() default -1;

    /**
     * 参与签名的body,-1就不取
     * @return
     */
    int bodyIndex() default -1;

    /**
     * 指定签名参数位置,-1为requestHeader里取
     * @return
     */
    int signatureIndex() default -1;

    /**
     * 请求头里加了白名单参数,可以忽略签名
     * @return
     */
    String[] whiteList() default {};
}
