package com.yangke.base.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.MessageFormat;

/**
 * @author keyang
 */
@Slf4j
public class RequestLoggingFilter extends CommonsRequestLoggingFilter {

    ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Override
    protected boolean shouldLog(HttpServletRequest request) {
        return log.isInfoEnabled();
    }

    @Override
    protected void beforeRequest(HttpServletRequest request, String message) {
        startTime.set(System.currentTimeMillis());
        log.info(MessageFormat.format("Before request [method={0},{1}]", request.getMethod(), message));
    }

    protected void afterRequest(HttpServletRequest request, String message, HttpServletResponse response) {
        log.info(
                MessageFormat.format("After request [method={0},{1},time={2}(ms),code={3}]", request.getMethod(), message,
                        (System.currentTimeMillis() - startTime.get()), response.getStatus()));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        boolean isFirstRequest = !isAsyncDispatch(request);
        HttpServletRequest requestToUse = request;

        if (isIncludePayload() && isFirstRequest && !(request instanceof ContentCachingRequestWrapper)) {
            requestToUse = new ContentCachingRequestWrapper(request, getMaxPayloadLength());
        }

        boolean shouldLog = shouldLog(requestToUse);
        if (shouldLog && isFirstRequest) {
            beforeRequest(requestToUse,
                    createMessage(requestToUse, DEFAULT_BEFORE_MESSAGE_PREFIX, DEFAULT_BEFORE_MESSAGE_SUFFIX));
        }
        try {
            filterChain.doFilter(requestToUse, response);
        } finally {
            if (shouldLog && !isAsyncStarted(requestToUse)) {
                afterRequest(requestToUse,
                        createMessage(requestToUse, DEFAULT_AFTER_MESSAGE_PREFIX, DEFAULT_AFTER_MESSAGE_SUFFIX), response);
            }
        }
    }
}
