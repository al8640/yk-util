package com.yangke.base.annotation;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;

import java.lang.annotation.*;

/**
 * @author ke.yang1
 * @description
 * @date 2022/6/14 5:44 下午
 */
@ConditionalOnExpression("'alitest'.equals('${spring.profiles.env}' || 'prod'.equals('${spring.profiles.env}'))")
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DevNotEffective {
}
