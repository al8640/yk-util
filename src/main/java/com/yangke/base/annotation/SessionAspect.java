package com.yangke.base.annotation;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * @author ke.yang1
 * @description 验证请求接口携带token合法性
 * @date 2020/8/6 1:44 下午
 */
@Slf4j
@Aspect
@Component
public class SessionAspect {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Around(value = "@annotation(SessionAuth)")
    public Object process(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String url = method.getAnnotation(SessionAuth.class).url();
        int tokenArgIndex = method.getAnnotation(SessionAuth.class).tokenArgIndex();
        String token = (String) args[tokenArgIndex];
        boolean tokenNull = token == null || "null".equals(token);
        boolean urlPassword = url.contains("/user/password/") && url.contains("manage");
        boolean urlLogout = url.contains("logout") ;
        if(!(tokenNull && urlPassword) && !urlLogout){
                processFilter(token);
        }
        return joinPoint.proceed();
    }
    private void processFilter(String token) {
//        if (StringUtils.isEmpty(token)) {
//            throw new ServiceException(ErrorEnum.NO_SIGN_EXCEPTION.code(),ErrorEnum.NO_SIGN_EXCEPTION.message());
//        }
//        LoginSessionVO sessionBean = (LoginSessionVO) redisTemplate.opsForValue().get(String.format(RedisConstant.LOGIN_SESSION_KEY, token));
//        if (sessionBean == null ) {
//            throw new ServiceException(ErrorEnum.NO_SIGN_EXCEPTION.code(),ErrorEnum.NO_SIGN_EXCEPTION.message());
//        }
    }
}
