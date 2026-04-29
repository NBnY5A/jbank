package com.victor.jbank.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class AuditInterceptor implements HandlerInterceptor {
    private final Logger logger = LoggerFactory.getLogger(AuditInterceptor.class);
    private final static String USER_IP_ADDRESS = "x-user-ip";

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws Exception {
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            @Nullable ModelAndView modelAndView
    ) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            @Nullable Exception ex
    ) throws Exception {
        logger.info(
                "Audit - Method: {}, URL: {}, IpAddress: {}, Status Code: {}",
                request.getMethod(),
                request.getRequestURI(),
                response.getHeader(USER_IP_ADDRESS),
                response.getStatus()
        );
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
