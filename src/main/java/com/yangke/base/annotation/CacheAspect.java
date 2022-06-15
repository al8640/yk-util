package com.yangke.base.annotation;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * @author keyang
 */
@Aspect
@Component
@Slf4j
public class CacheAspect {


    @Resource
    private RedisTemplate<String,String> redisTemplate;

    @Around(value = "@annotation(Cache)")
    public Object process(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取方法入参参数数组
        // 扩展 自定义注解 可添加一个属性 argIndex 可获取某个索引位置对应的参数值
        Object[] args = joinPoint.getArgs();
        // 获取方法签名
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        // 获取方法
        Method method = signature.getMethod();
        // 获取注解中定义的参数
        String key = method.getAnnotation(Cache.class).key();
        boolean nullCache = method.getAnnotation(Cache.class).nullCache();
        int ttl = method.getAnnotation(Cache.class).ttl();
        try {
            for (int i = 0; i < args.length; i++) {
                key = key.replace("{" + i + "}", toPrimitive(args[i]));
            }
            String cacheValue = redisTemplate.opsForValue().get(key);
            if (StringUtils.isNotBlank(cacheValue)) {
//                LOGGER.info("get value from cache, cache key:{}, value:{}",key, cacheValue);
                return JSON.parseObject(cacheValue, getReturnType(getTargetMethod(joinPoint)));
            } else {
                Object obj = joinPoint.proceed();
                String value;
                if (null == obj) {
                    if (!nullCache) {
                        return obj;
                    }
                    value = "";
                } else {
                    value = JSON.toJSONString(obj);
                }
                if (ttl != -1) {
                    redisTemplate.opsForValue().set(key, value, ttl);
                } else {
                    redisTemplate.opsForValue().set(key, value);
                }
                log.debug("get value from db server!");
                return obj;
            }
        } catch (Exception e) {
            log.error("get value from cache error key:" + key, e);
            return joinPoint.proceed();
        }
    }

    /**
     * 获取方法返回值类型
     * @param method
     * @return
     */
    private Type getReturnType(Method method) {
        return method.getGenericReturnType();
    }

    /**
     * 获取注解类中的目标方法
     *
     * @param joinPoint
     * @return
     * @throws Exception
     */
    private final Method getTargetMethod(ProceedingJoinPoint joinPoint) throws Exception {
        Method superMethod = getMethodFromInterface(joinPoint);
        Class<?> targetClass = joinPoint.getTarget().getClass();
        return targetClass.getMethod(superMethod.getName(), superMethod.getParameterTypes());
    }

    /**
     * 获取接口中方法的定义
     *
     * @param joinPoint
     * @return
     * @throws Exception
     */
    private final Method getMethodFromInterface(ProceedingJoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        return methodSignature.getMethod();
    }

    private String toPrimitive(Object obj) {
        if (obj instanceof Integer) {
            return String.valueOf(obj);
        } else if (obj instanceof Float) {
            return String.valueOf(((Float) obj).floatValue());
        } else if (obj instanceof Double) {
            return String.valueOf(((Double) obj).doubleValue());
        } else if (obj instanceof Long) {
            return String.valueOf(((Long) obj).longValue());
        } else if (obj instanceof Character) {
            return String.valueOf(((Character) obj).charValue());
        } else if (obj instanceof Short) {
            return String.valueOf(((Short) obj).shortValue());
        } else if (obj instanceof Boolean) {
            return String.valueOf(((Boolean) obj).booleanValue());
        } else if (obj instanceof Byte) {
            return String.valueOf(((Byte) obj).byteValue());
        }
        return String.valueOf(obj);
    }

}
