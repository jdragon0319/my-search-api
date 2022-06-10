package com.example.mysearchapi.common.web;



import com.example.mysearchapi.common.logging.CollectLoggingAspect;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CollectLoggingInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        CollectLoggingAspect.reset();
        return true;
    }

}
