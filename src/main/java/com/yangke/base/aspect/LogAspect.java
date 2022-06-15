package com.yangke.base.aspect;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * @author caoming
 */
@Slf4j
@Aspect
@Component
@EnableAspectJAutoProxy(exposeProxy = true)
public class LogAspect {
    public static final String TRACE_LOG_ID="traceLogId";

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void pointcut() {

    }

    /**
     * 记录请求日志信息
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("pointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch watch = new StopWatch();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String traceLogId = request.getHeader(TRACE_LOG_ID);
        if(StringUtils.isBlank(traceLogId)){
            traceLogId = UUID.randomUUID().toString();
        }
        MDC.put(TRACE_LOG_ID, traceLogId);
        Object[] args = joinPoint.getArgs();
        String url = request.getRequestURI();
        String method = request.getMethod();
        Object object = null;
        try {
            watch.start();
            object = joinPoint.proceed();
            watch.stop();
        } catch (Exception e) {
            log.error("执行方法异常:{},traceLogId:{},url:{},method:{},request params:{}", e, traceLogId,url, method,
                    JSON.toJSONString(args));
            throw e;
        }finally {
            MDC.clear();
        }
        Long cost = watch.getTime();
        log.info("收到的请求信息 traceLogId:{}, url:{},method:{},request params:{},response:{},costs:{}ms.", traceLogId,url,
                method, JSON.toJSONString(args), JSON.toJSONString(object), cost);
        return object;
    }
}
