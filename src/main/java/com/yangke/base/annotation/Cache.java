package com.yangke.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author keyang
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cache {
    /**
     * 缓存key
     * @return
     */
    String key();

    /**
     * 空数据是否缓存，默认是
     * @return
     */
    boolean nullCache() default true;

    /**
     * 缓存时间,秒，-1表示不过期
     * @return
     */
    int ttl() default -1;
}
