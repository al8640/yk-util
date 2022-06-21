package com.yangke.base.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @Author: ke.yang1
 * @Date: 2022/06/16
 * @Version: 1.0
 * @Describe:用于生成全局 traceLogId
 */
@Aspect
@Component
@Order(1)
public class TraceLogIdAspect {

    /**
     * trace id
     */
    private static final String TRACE_ID = "TRACE_ID";

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void pointCutLog() {
    }

    @Before("pointCutLog()")
    public void createUUID(JoinPoint joinPoint) {
        //简易版，只使用 uuid
        UUID uuid = UUID.randomUUID();
        //添加 MDC
        MDC.put(TRACE_ID, uuid.toString());
    }


    /**
     * 清理threadLocal
     *
     * @param joinPoint
     */
    @After("pointCutLog()")
    public void logAfter(JoinPoint joinPoint) {
        //移除 MDC
        MDC.remove(TRACE_ID);
    }
}
