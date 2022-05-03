package com.yangke.util.annotation;

import com.yangke.util.aspect.RequestHelper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @author ke.yang1
 * @description
 * @date 2021/8/4 2:36 下午
 */
@Component
@Aspect
public class RequestSignatureAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestSignatureAspect.class);

    @Around(value = "@annotation(RequestSignature)", argNames = "joinPoint,RequestSignature")
    public Object process(ProceedingJoinPoint joinPoint, RequestSignature requestSignature) throws Throwable {
        HttpServletRequest request = RequestHelper.getRequest();
        if (request == null) {
            return joinPoint.proceed();
        }
        //判断白名单里
        for (String whiteList : requestSignature.whiteList()) {
            if (whiteList != null && whiteList.equals(request.getHeader("whiteList"))) {
                return joinPoint.proceed();
            }
        }
        try {
            //判断时间戳
            String timestamp = request.getHeader("timestamp");
            if (timestamp == null || timestamp.isEmpty()) {
                return writeFailed();
            }
            //时间戳在一天以内
            long currentTime = System.currentTimeMillis();
            long time = Long.valueOf(timestamp);
            if ((currentTime - time > 86400000) || (time - currentTime > 86400000)) {
                return writeFailed();
            }
            Object[] args = joinPoint.getArgs();
            //参与签名的固定值
            String fixedValue = requestSignature.fixed();
            //参与签名的参数
            int[] param = requestSignature.paramIndex();
            if (param.length > 0 && param[0] != -1) {
                for (int index : param) {
                    fixedValue += args[index] == null ? "" : args[index].toString();
                }
            } else {
                ArrayList<String> names = Collections.list(request.getParameterNames());
                Collections.sort(names);
                for (String name : names) {
                    String value = request.getParameter(name);
                    fixedValue += value == null ? "" : value;
                }
                int bodyIndex = requestSignature.bodyIndex();
                if (bodyIndex > -1) {
                    fixedValue += args[bodyIndex];
                }
            }
            //参与签名的时间戳
            fixedValue += timestamp;
            String md5 = DigestUtils.md5DigestAsHex(fixedValue.getBytes(StandardCharsets.UTF_8));
            //客户端签名值
            String signature = requestSignature.signatureIndex() == -1 ? request.getHeader("signature") : String.valueOf(args[requestSignature.signatureIndex()]);
            if (!md5.equals(signature)) {
                LOGGER.debug("signature failed origin:[{}] md5:[{}] signature:[{}]", fixedValue, md5, signature);
                return writeFailed();
            }
        } catch (NumberFormatException e) {
            LOGGER.error("RequestSignatureAspect process error", e);
            return writeFailed();
        } catch (Exception e) {
            LOGGER.error("RequestSignatureAspect process error", e);
            return joinPoint.proceed();
        }
        return joinPoint.proceed();
    }

    private Object writeFailed() throws Exception {
        HttpServletResponse response = RequestHelper.getResponse();
        response.setStatus(400);
        try (PrintWriter writer = response.getWriter()) {
            writer.write("authentication failed");
        }
        return null;
    }
}
